package medical_uservice.mapper;

import medical_uservice.entity.dto.CreatePatientDto;
import medical_uservice.entity.dto.GetPatientDto;
import medical_uservice.entity.Patient;
import org.bson.assertions.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class PatientMapperTest {

	@Test
	void toEntity_ValidDto_ReturnsExpectedEntity() {
		// Arrange
		CreatePatientDto dto = new CreatePatientDto("John", "Doe", "1990-01-01", "O+");

		// Act
		Patient entity = PatientMapper.toEntity(dto);

		// Assert
		assertEquals(dto.getFirstName(), entity.getFirstName());
		assertEquals(dto.getLastName(), entity.getLastName());
		assertEquals(dto.getDob(), entity.getDob());
		assertEquals(dto.getBloodType(), entity.getBloodType());
	}

	@Test
	void toEntity_NullDto_ReturnsNullEntity() {

		// Assert
		assertNull(PatientMapper.toEntity(null));
	}

	@Test
	void updateEntity_NullDto_ReturnsSameEntity() {

		// Arrange
		Patient entity = new Patient("John", "Doe", "1990-01-01", "O+");
		PatientMapper.updateEntityFromDto(null, entity);
		// Assert
		assertNotNull(entity);
	}


	@Test
	void toDto_ValidEntity_ReturnsExpectedDto() {
		// Arrange
		Patient entity = new Patient("John", "Doe", "1990-01-01", "O+");

		// Act
		GetPatientDto dto = new PatientMapper().toDto(entity);

		// Assert
		assertEquals(entity.getFirstName(), dto.getFirstName());
		assertEquals(entity.getLastName(), dto.getLastName());
		assertEquals(entity.getDob(), dto.getDob());
	}

	@Test
	void toDto_NullEntity_ReturnsNull() {
		// Act
		GetPatientDto dto = new PatientMapper().toDto(null);

		// Assert
		assertNull(dto);
	}

	@Test
	void updateEntityFromDto_ValidDtoAndEntity_UpdatesEntityFields() {
		// Arrange
		CreatePatientDto dto = new CreatePatientDto("John", "Doe", "1990-01-01", "O+");
		Patient entity = new Patient();

		// Act
		PatientMapper.updateEntityFromDto(dto, entity);

		// Assert
		assertEquals(dto.getFirstName(), entity.getFirstName());
		assertEquals(dto.getLastName(), entity.getLastName());
		assertEquals(dto.getDob(), entity.getDob());
		assertEquals(dto.getBloodType(), entity.getBloodType());
		assertEquals(PatientMapper.generateId(dto.getFirstName(), dto.getLastName(), dto.getDob()), entity.getId());
	}

	@Test
	void generateId_ValidData_ReturnsExpectedId() {
		// Arrange
		String firstName = "John";
		String lastName = "Doe";
		String dob = "1990-01-01";
		String expectedId = "johndoe1990-01-01";

		// Act
		String actualId = PatientMapper.generateId(firstName, lastName, dob);

		// Assert
		assertEquals(expectedId, actualId);
	}

	@Test
	void generateId_NullData_ReturnsNull() {

		// Assert
		assertNull(PatientMapper.generateId(null, null, null));
	}
}

