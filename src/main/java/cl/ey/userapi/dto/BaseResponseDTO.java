package cl.ey.userapi.dto;

import cl.ey.userapi.type.ResultCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BaseResponseDTO<T> {
	private ResultCode resultado;
	private String mensaje;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private T datos;
}
