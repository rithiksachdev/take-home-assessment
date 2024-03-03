package medical_uservice.controller;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import medical_uservice.entity.Patient;
import medical_uservice.entity.dto.CreatePatientDto;
import medical_uservice.entity.dto.GetPatientDto;
import medical_uservice.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class PatientControllerTest {

	@InjectMocks
	private PatientController patientController;

	@Mock
	private PatientService patientService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCreatePatient() {
		// Create a sample CreatePatientDto
		CreatePatientDto createPatientDto = new CreatePatientDto();
		createPatientDto.setFirstName("John");
		createPatientDto.setLastName("Doe");
		createPatientDto.setDob("1990-01-01");
		createPatientDto.setBloodType("A+");

		// Create a sample Patient entity
		Patient createdPatient = new Patient();
		createdPatient.setId("1");
		createdPatient.setFirstName("John");
		createdPatient.setLastName("Doe");
		createdPatient.setDob("1990-01-01");
		createdPatient.setBloodType("A+");

		// Mock the service method to return the created patient
		when(patientService.createPatient(any(CreatePatientDto.class))).thenReturn(createdPatient);

		// Call the controller method
		ResponseEntity<Patient> responseEntity = patientController.createPatient(createPatientDto);

		// Verify the response status
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

		// Verify the response body contains the patient ID
		assertNotNull(responseEntity.getBody());
		assertEquals("1", responseEntity.getBody().getId());
	}

	@Test
	void testGetAllPatients() {
		// Create sample patients
		List<Patient> patients = new ArrayList<>();
		Patient patient1 = new Patient();
		patient1.setId("1");
		patient1.setFirstName("John");
		patient1.setLastName("Doe");
		patient1.setDob("1990-01-01");
		patient1.setBloodType("A+");
		patients.add(patient1);

		Patient patient2 = new Patient();
		patient2.setId("2");
		patient2.setFirstName("Jane");
		patient2.setLastName("Doe");
		patient2.setDob("1992-05-15");
		patient2.setBloodType("B-");
		patients.add(patient2);

		// Mock the service method to return the sample patients
		when(patientService.getAllPatients()).thenReturn(patients);

		// Call the controller method
		ResponseEntity<List<Patient>> responseEntity = patientController.getAllPatients();

		// Verify the response status
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		// Verify the response body contains the correct number of patients
		assertNotNull(responseEntity.getBody());
		assertEquals(2, responseEntity.getBody().size());
	}

	@Test
	void testGetPatientById() {
		// Create a sample patient
		Patient patient = new Patient();
		patient.setId("1");
		patient.setFirstName("John");
		patient.setLastName("Doe");
		patient.setDob("1990-01-01");
		patient.setBloodType("A+");

		// Mock the service method to return the sample patient
		when(patientService.getPatientById("John","Doe", "1990-01-01")).thenReturn(patient);

		// Call the controller method
		ResponseEntity<Patient> responseEntity = patientController.getPatientById("John","Doe", "1990-01-01");

		// Verify the response status
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		// Verify the response body contains the correct patient
		assertNotNull(responseEntity.getBody());
		assertEquals("1", responseEntity.getBody().getId());
		assertEquals("John", responseEntity.getBody().getFirstName());
		assertEquals("Doe", responseEntity.getBody().getLastName());
		assertEquals("1990-01-01", responseEntity.getBody().getDob());
		assertEquals("A+", responseEntity.getBody().getBloodType());
	}

	@Test
	void testUpdatePatient() {
		// Create a sample CreatePatientDto
		CreatePatientDto createPatientDto = new CreatePatientDto();
		createPatientDto.setFirstName("John");
		createPatientDto.setLastName("Doe");
		createPatientDto.setDob("1990-01-01");
		createPatientDto.setBloodType("A+");

		// Create a sample GetPatientDto
		Patient patient = new Patient();
		patient.setId("1");
		patient.setFirstName("John");
		patient.setLastName("Doe");
		patient.setDob("1990-01-01");
		patient.setBloodType("A+");

		// Mock the service method to return the updated patient DTO
		when(patientService.updatePatient("1", createPatientDto)).thenReturn(patient);

		// Call the controller method
		ResponseEntity<Patient> responseEntity = patientController.updatePatient("1", createPatientDto);

		// Verify the response status
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		// Verify the response body contains the correct updated patient DTO
		assertNotNull(responseEntity.getBody());
		assertEquals("John", responseEntity.getBody().getFirstName());
		assertEquals("Doe", responseEntity.getBody().getLastName());
		assertEquals("1990-01-01", responseEntity.getBody().getDob());
	}

	@Test
	void testDeletePatient() {
		// Call the controller method
		ResponseEntity<Void> responseEntity = patientController.deletePatient("1");

		// Verify the response status
		assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

	}
}
