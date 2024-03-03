package medical_uservice.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "patients")
@Getter
@Setter
@NoArgsConstructor
public class Patient {
    @Id
    private String id; // Custom ID
    private String firstName;
    private String lastName;
    private String dob; // using ISO format (YYYY-MM-DD) for consistency
    private String bloodType;

    public Patient(String firstName, String lastName, String dob, String bloodType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.bloodType = bloodType;
        this.id = generateId(firstName, lastName, dob); // Set the custom ID
    }

    private String generateId(String firstName, String lastName, String dob) {
        // Normalize and concatenate fields to form the ID
        return (firstName.trim() + lastName.trim() + dob).replaceAll("\\s+", "").toLowerCase();
    }
}
