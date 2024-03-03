package medical_uservice.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import java.util.List;
import java.util.Optional;
import medical_uservice.PatientApplication;
import medical_uservice.entity.dto.GetPatientDto;
import medical_uservice.exception.PatientNotFoundException;
import org.junit.jupiter.api.Test;

import medical_uservice.entity.dto.CreatePatientDto;
import medical_uservice.entity.Patient;
import medical_uservice.exception.CustomException;
import medical_uservice.mapper.PatientMapper;
import medical_uservice.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class PatientServiceTest {

    @Test
    void ApplicationTest(){
        assertThatCode(() -> PatientApplication.main(new String[]{})).doesNotThrowAnyException();
    }


    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PatientMapper patientMapper;

    @InjectMocks
    private PatientService patientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPatient_ValidDto_ReturnsCreatedPatient() {
        // Arrange
        CreatePatientDto createPatientDto = new CreatePatientDto("John", "Doe", "1990-01-01", "O+");
        Patient expectedPatient = new Patient("John", "Doe", "1990-01-01", "O+");

        // Mock the behavior of patientRepository.existsByFirstNameAndLastNameAndDob()
        when(patientRepository.existsByFirstNameAndLastNameAndDob(any(), any(), any())).thenReturn(false);

        // Mock the behavior of patientRepository.save()
        when(patientRepository.save(any())).thenReturn(expectedPatient);

        // Act
        Patient actualPatient = patientService.createPatient(createPatientDto);

        // Assert
        assertNotNull(actualPatient);
        assertEquals(expectedPatient, actualPatient);
    }


    @Test
    void createPatient_InvalidDateFormat_ThrowsCustomException() {
        // Arrange
        CreatePatientDto createPatientDto = new CreatePatientDto("John", "Doe", "invalid_date", "O+");

        // Act & Assert
        assertThrows(CustomException.class, () -> patientService.createPatient(createPatientDto));
    }

    @Test
    void createPatient_PatientAlreadyExists_ThrowsCustomException() {
        // Arrange
        CreatePatientDto createPatientDto = new CreatePatientDto("John", "Doe", "1990-01-01", "O+");
        when(patientRepository.existsByFirstNameAndLastNameAndDob(any(), any(), any())).thenReturn(true);

        // Act & Assert
        assertThrows(CustomException.class, () -> patientService.createPatient(createPatientDto));
    }

    @Test
    void getAllPatients() {
        // Arrange
        List<Patient> expectedPatients = List.of(new Patient(), new Patient());
        when(patientRepository.findAll()).thenReturn(expectedPatients);

        // Act
        List<Patient> actualPatients = patientService.getAllPatients();

        // Assert
        assertEquals(expectedPatients.size(), actualPatients.size());
        assertTrue(actualPatients.containsAll(expectedPatients));
    }

    @Test
    void getPatientById_PatientExists_ReturnsPatient() {
        // Arrange
        Patient expectedPatient = new Patient();
        when(patientRepository.findById(anyString())).thenReturn(Optional.of(expectedPatient));

        // Act
        Patient actualPatient = patientService.getPatientById("John", "Doe", "1990-01-01");

        // Assert
        assertNotNull(actualPatient);
        assertEquals(expectedPatient, actualPatient);
    }

    @Test
    void getPatientById_PatientDoesNotExist_ThrowsException() {
        // Arrange
        when(patientRepository.findById(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(
            PatientNotFoundException.class, () -> patientService.getPatientById("John", "Doe", "1990-01-01"));
    }

    @Test
    void updatePatient_PatientExists_ReturnsUpdatedDto() {
        // Arrange
        String id = "123";
        CreatePatientDto updatedPatientDto = new CreatePatientDto();
        Patient existingPatient = new Patient();
        existingPatient.setId(id);
        Patient savedPatient = new Patient();
        GetPatientDto expectedDto = new GetPatientDto();

        when(patientRepository.findById(anyString())).thenReturn(Optional.of(existingPatient));
        when(patientRepository.existsById(anyString())).thenReturn(true);
        doNothing().when(patientRepository).deleteById(anyString());
        when(patientRepository.save(existingPatient)).thenReturn(savedPatient);
        when(patientMapper.toDto(savedPatient)).thenReturn(expectedDto);

        // Act
        Patient patient = patientService.updatePatient(id, updatedPatientDto);

        // Assert
        assertNotNull(patient);
    }

    @Test
    void updatePatient_PatientDoesNotExist_ThrowsException() {
        // Arrange
        String id = "123";
        CreatePatientDto updatedPatientDto = new CreatePatientDto();

        when(patientRepository.findById(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PatientNotFoundException.class, () -> patientService.updatePatient(id, updatedPatientDto));
    }

    @Test
    void deletePatient_PatientExists_DeletesPatient() {
        // Arrange
        String id = "123";
        when(patientRepository.existsById(id)).thenReturn(true);

        // Act
        assertDoesNotThrow(() -> patientService.deletePatient(id));

        // Assert
        verify(patientRepository, times(1)).deleteById(id);
    }

    @Test
    void deletePatient_PatientDoesNotExist_ThrowsException() {
        // Arrange
        String id = "123";
        when(patientRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(PatientNotFoundException.class, () -> patientService.deletePatient(id));
    }

}
