package cl.ey.userapi.controller;

import cl.ey.userapi.dto.BaseResponseDTO;
import cl.ey.userapi.dto.CreateUserDTO;
import cl.ey.userapi.dto.UserDTO;
import cl.ey.userapi.exception.UserEmailAlreadyRegisterException;
import cl.ey.userapi.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

	@InjectMocks
	UserController userController;

	@Mock
	private UserService userService;

	CreateUserDTO createUserDTO;
	UserDTO userDTO;
	List<UserDTO> userDTOList;

	@BeforeEach
	void init() {
		createUserDTO = new CreateUserDTO();
		userDTO = new UserDTO();
		userDTOList = new ArrayList<>();
	}

	@Test
	void createUser_ok() throws UserEmailAlreadyRegisterException {
		when(userService.create(createUserDTO)).thenReturn(userDTO);

		BaseResponseDTO<UserDTO> respuesta = userController.createUser(createUserDTO);
		assertNotNull(respuesta);
		assertNotNull(respuesta.getDatos());
	}

	@Test
	void getUsers_ok(){
		when(userService.getUsers()).thenReturn(userDTOList);

		BaseResponseDTO<List<UserDTO>> respuesta = userController.getUsers();
		assertNotNull(respuesta);
		assertNotNull(respuesta.getDatos());
	}
}
