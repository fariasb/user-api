package cl.ey.userapi.controller;

import cl.ey.userapi.dto.BaseResponseDTO;
import cl.ey.userapi.dto.CreateUserDTO;
import cl.ey.userapi.dto.UserDTO;
import cl.ey.userapi.exception.UserEmailAlreadyRegisterException;
import cl.ey.userapi.service.UserService;
import cl.ey.userapi.type.ResultCode;
import cl.ey.userapi.util.MessageSourceUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value = "API para gestion de usuarios")
@Controller
@RequestMapping
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("user")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Permite la creaciíon de un usuario", response = BaseResponseDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Usuario creado", response = BaseResponseDTO.class),
			@ApiResponse(code = 400, message = "Error en la validacion de datos enviados", response = BaseResponseDTO.class),
			@ApiResponse(code = 409, message = "Correo de usuario ya registrado", response = BaseResponseDTO.class)})
	@ResponseBody
	public BaseResponseDTO<UserDTO> createUser(@Valid @RequestBody CreateUserDTO createUserDTO) throws UserEmailAlreadyRegisterException {
		UserDTO userDTO = userService.create(createUserDTO);
		return new BaseResponseDTO<>(ResultCode.SUCCESS, MessageSourceUtil.getValue("msge.servicio.exitoso"), userDTO);
	}

	@GetMapping("user")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Lista los usuarios creados", response = BaseResponseDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Se obtiene listado exitosamente", response = BaseResponseDTO.class)})
	@ResponseBody
	public BaseResponseDTO<List<UserDTO>> getUsers(){
		List<UserDTO> userDTOList = userService.getUsers();
		return new BaseResponseDTO<>(ResultCode.SUCCESS, MessageSourceUtil.getValue("msge.servicio.exitoso"), userDTOList);
	}
}
