package medical_uservice.repository;

import medical_uservice.entity.Patient;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends MongoRepository<Patient, String> {

    //List<Patient> findByUserId(Long userId);

    boolean existsByFirstNameAndLastNameAndDob(String firstName, String lastName, String dob);

    List<Patient> findByFirstNameAndLastNameAndDob(String firstName, String lastName, String dob);
}
