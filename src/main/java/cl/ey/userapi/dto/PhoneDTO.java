package cl.ey.userapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhoneDTO {
	private String number;
	private String citycode;
	private String contrycode;
}
