package medical_uservice.service;

import lombok.RequiredArgsConstructor;
import medical_uservice.entity.dto.CreatePatientDto;
import medical_uservice.entity.dto.GetPatientDto;
import medical_uservice.entity.Patient;
import medical_uservice.exception.CustomException;
import medical_uservice.exception.PatientNotFoundException;
import medical_uservice.mapper.PatientMapper;
import medical_uservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
    private static final String PATIENT_NOT_FOUND = "Patient not found";



    public Patient createPatient(CreatePatientDto createPatientDto) {
        if (!isValidDateFormat(createPatientDto.getDob())) {
            throw new CustomException("Invalid date format for DOB. Please use " + DATE_PATTERN);
        }

        // Check if patient already exists in the database
        if (patientRepository.existsByFirstNameAndLastNameAndDob(
            createPatientDto.getFirstName(), createPatientDto.getLastName(), createPatientDto.getDob())) {
            throw new CustomException("Patient already exists with the given name and DOB.");
        }

        Patient patient = PatientMapper.toEntity(createPatientDto);

        return patientRepository.save(patient);
    }

    public List<Patient> getAllPatients() {
        List<Patient> patients = patientRepository.findAll();
        return patients;
    }

    public Patient getPatientById(String firstname, String lastname, String dob) {
        String id = PatientMapper.generateId(firstname, lastname, dob);
        return patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException(PATIENT_NOT_FOUND));
    }

    public Patient updatePatient(String id, CreatePatientDto updatedPatientDto) {
        Patient existingPatient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException(PATIENT_NOT_FOUND));
        // Update fields manually or use a utility method/mapper to update the entity from DTO
        deletePatient(existingPatient.getId());
        PatientMapper.updateEntityFromDto(updatedPatientDto, existingPatient);
	    return patientRepository.save(existingPatient);
    }

    public void deletePatient(String id) {
        if (!patientRepository.existsById(id)) {
            throw new PatientNotFoundException(PATIENT_NOT_FOUND);
        }
        patientRepository.deleteById(id);
    }

    private boolean isValidDateFormat(String dateStr) {
        try {
            LocalDate.parse(dateStr, dateFormatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
