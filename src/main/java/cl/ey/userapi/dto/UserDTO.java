package cl.ey.userapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserDTO {
	private String id;
	private Date created;
	private Date modified;
	@JsonProperty("last_login")
	private Date lastLogin;
	private String token;
	@JsonProperty("isactive")
	private Boolean active;
}
