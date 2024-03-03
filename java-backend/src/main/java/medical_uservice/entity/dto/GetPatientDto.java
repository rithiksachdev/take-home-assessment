package medical_uservice.entity.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetPatientDto implements Serializable {
	private String firstName;
	private String lastName;
	private String dob;
}
