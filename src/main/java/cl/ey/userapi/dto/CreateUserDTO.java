package cl.ey.userapi.dto;

import cl.ey.userapi.validation.ExtendedEmailValidator;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@Setter
public class CreateUserDTO {

	private String name;

	@ExtendedEmailValidator
	@NotNull
	private String email;

	@NotEmpty
	@Pattern(regexp = "[A-Z][a-z]+[0-9]{2}", message = "msge.error.password.format")
	private String password;

	private List<PhoneDTO> phones;
}
