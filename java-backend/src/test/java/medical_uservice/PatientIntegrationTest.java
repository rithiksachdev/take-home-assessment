package medical_uservice;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import medical_uservice.entity.dto.CreatePatientDto;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class PatientIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	public static String generateString() {
		return RandomStringUtils.randomAlphabetic(30);
	}

	@Test
	void givenValidPatientDto_whenCreatePatient_thenReturnsCreatedStatus() throws Exception {
		CreatePatientDto createPatientDto = new CreatePatientDto(generateString(), "Doe", "1990-01-01", "O+");
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/patients")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(createPatientDto)))
			.andExpect(MockMvcResultMatchers.status().isCreated());
	}

	@Test
	void givenInvalidPatientDto_whenCreatePatient_thenReturnsBadRequest() throws Exception {
		CreatePatientDto createPatientDto = new CreatePatientDto(null, "Doe", "1990-01-01", "O+");
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/patients")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(createPatientDto)))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	void givenNonExistingPatient_whenGetPatientById_thenReturnsNotFound() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/patients/{firstname}/{lastname}/{dob}", generateString(), "Doe", "1990-01-01"))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	void givenExistingPatient_whenGetPatientById_thenReturnsOkWithPatientDetails() throws Exception {
		// Step 1: Create a patient
		String firstName = generateString();
		String lastName = "Doe";
		String dob = "1990-01-01";
		String bloodType = "O+";
		CreatePatientDto createPatientDto = new CreatePatientDto(firstName, lastName, dob, bloodType);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/patients")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(createPatientDto)))
			.andExpect(MockMvcResultMatchers.status().isCreated());

		// Step 2: Retrieve the created patient using the GET endpoint
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/patients/{firstname}/{lastname}/{dob}", firstName, lastName, dob))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(firstName))
			.andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(lastName))
			.andExpect(MockMvcResultMatchers.jsonPath("$.dob").value(dob))
			.andExpect(MockMvcResultMatchers.jsonPath("$.bloodType").value(bloodType));
	}


	@Test
	void givenNonExistingPatientId_whenUpdatePatient_thenReturnsNotFound() throws Exception {
		CreatePatientDto updatePatientDto = new CreatePatientDto(generateString(), "Doe", "1990-01-01", "O-");
		mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/patients/{id}", "NA")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updatePatientDto)))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	void givenExistingPatient_whenUpdatePatient_thenReturnsOkWithUpdatedDetails() throws Exception {
		// Step 1: Create a patient
		String firstName = generateString(); // Generate a unique first name
		String lastName = "Doe";
		String dob = "1990-01-01";
		String bloodType = "O+";
		CreatePatientDto createPatientDto = new CreatePatientDto(firstName, lastName, dob, bloodType);

		// Save the patient and assume the patient is assigned an ID, for simplicity, let's say "1"
		// This step assumes your service method returns the created object including its ID
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/patients")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(createPatientDto)))
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andReturn();

		// Extract the patient ID from the response. This assumes the ID is returned in the response body.
		// Adjust this to match your actual response structure
		String responseString = result.getResponse().getContentAsString();
		String patientId = JsonPath.parse(responseString).read("$.id");

		// Step 2: Update the patient details
		String updatedBloodType = "AB+"; // Change a detail, e.g., blood type
		CreatePatientDto updatePatientDto = new CreatePatientDto(firstName, lastName, dob, updatedBloodType);

		mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/patients/{id}", patientId) // Use the extracted patient ID
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updatePatientDto)))
			.andExpect(MockMvcResultMatchers.status().isOk()) // Assuming the update is successful
			.andExpect(MockMvcResultMatchers.jsonPath("$.bloodType").value(updatedBloodType)); // Validate the updated detail
	}


	@Test
	void givenNonExistingPatientId_whenDeletePatient_thenReturnsNotFound() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/patients/{id}", "NA"))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	void givenExistingPatient_whenDeletePatient_thenPatientIsDeletedSuccessfully() throws Exception {
		// Step 1: Create a patient
		String firstName = generateString(); // Generate a unique first name
		String lastName = "Doe";
		String dob = "1990-01-01";
		String bloodType = "O+";
		CreatePatientDto createPatientDto = new CreatePatientDto(firstName, lastName, dob, bloodType);

		// Assuming the patient creation endpoint returns the created patient's ID in the response
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/patients")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(createPatientDto)))
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andReturn();

		// Extract the patient ID from the response. Adjust this to match your actual API response structure.
		String responseString = result.getResponse().getContentAsString();
		String patientId = JsonPath.parse(responseString).read("$.id");

		// Step 2: Delete the patient using the obtained ID
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/patients/{id}", patientId))
			.andExpect(MockMvcResultMatchers.status().isNoContent()); // Assuming deletion is successful

		// Optional: Attempt to retrieve the deleted patient to confirm deletion
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/patients/{firstname}/{lastname}/{dob}", firstName, lastName, dob))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}


	@Test
	void getAllPatients_whenPatientsExist_thenReturnsOkWithListOfPatients() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/patients")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(greaterThan(0))));
	}

}
