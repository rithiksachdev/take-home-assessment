package medical_uservice.controller;

import javax.validation.Valid;
import medical_uservice.entity.Patient;
import medical_uservice.entity.dto.CreatePatientDto;
import medical_uservice.entity.dto.GetPatientDto;
import medical_uservice.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    public ResponseEntity<Patient> createPatient(@RequestBody @Valid CreatePatientDto createPatientDto) {
        Patient createdPatient = patientService.createPatient(createPatientDto);
        return new ResponseEntity<>(createdPatient, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/{firstname}/{lastname}/{dob}")
    public ResponseEntity<Patient> getPatientById(@PathVariable String firstname,@PathVariable String lastname,@PathVariable String dob) {
        Patient patient = patientService.getPatientById(firstname, lastname, dob);
        return ResponseEntity.ok(patient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable String id, @RequestBody CreatePatientDto createPatientDto) {
        Patient updatedPatient = patientService.updatePatient(id, createPatientDto);
        return ResponseEntity.ok(updatedPatient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable String id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
