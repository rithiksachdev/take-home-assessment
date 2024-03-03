package medical_uservice.mapper;

import medical_uservice.entity.dto.CreatePatientDto;
import medical_uservice.entity.dto.GetPatientDto;
import medical_uservice.entity.Patient;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper {

	public static Patient toEntity(CreatePatientDto dto) {
		if (dto == null) {
			return null;
		}
		return new Patient(
			dto.getFirstName(), dto.getLastName(), dto.getDob(), dto.getBloodType()
		);
	}

	public GetPatientDto toDto(Patient entity) {
		if (entity == null) {
			return null;
		}
		GetPatientDto dto = new GetPatientDto();
		dto.setFirstName(entity.getFirstName());
		dto.setLastName(entity.getLastName());
		dto.setDob(entity.getDob());
		return dto;
	}

	public static void updateEntityFromDto(CreatePatientDto dto, Patient entity) {
		if (dto == null || entity == null) {
			return;
		}
		entity.setFirstName(dto.getFirstName());
		entity.setLastName(dto.getLastName());
		entity.setDob(dto.getDob());
		entity.setBloodType(dto.getBloodType());
		entity.setId(generateId(dto.getFirstName(), dto.getLastName(), dto.getDob()));
	}

	public static String generateId(String firstName, String lastName, String dob) {
		if (firstName == null || lastName == null || dob == null) {
			return null;
		}
		return (firstName.trim() + lastName.trim() + dob.trim()).replaceAll("\\s+", "").toLowerCase();
	}
}
