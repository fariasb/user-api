package cl.ey.userapi.service;

import cl.ey.userapi.dto.CreateUserDTO;
import cl.ey.userapi.dto.PhoneDTO;
import cl.ey.userapi.dto.UserDTO;
import cl.ey.userapi.exception.UserEmailAlreadyRegisterException;
import cl.ey.userapi.model.UserModel;
import cl.ey.userapi.repository.UserRepository;
import cl.ey.userapi.service.impl.UserServiceImpl;
import cl.ey.userapi.util.JwtTokenUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

	@InjectMocks
	UserServiceImpl userService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private JwtTokenUtil jwtTokenUtil;

	CreateUserDTO createUserDTO;
	List<UserModel> userDTOList;

	@BeforeEach
	void init() {
		createUserDTO = new CreateUserDTO();
		createUserDTO.setPassword("pasword");
		createUserDTO.setEmail("email");
		createUserDTO.setPhones(new ArrayList<>());
		createUserDTO.getPhones().add(new PhoneDTO());

		userDTOList = new ArrayList<>();
		userDTOList.add(new UserModel());
		userDTOList.get(0).setUuid(UUID.randomUUID());
	}

	@Test
	void create_ok_without_phones() throws UserEmailAlreadyRegisterException {
		createUserDTO.setPhones(null);
		when(userRepository.findByEmail(createUserDTO.getEmail())).thenReturn(null);

		UserDTO respuesta = userService.create(createUserDTO);
		assertNotNull(respuesta);
	}

	@Test
	void create_ok_with_phones() throws UserEmailAlreadyRegisterException {
		when(userRepository.findByEmail(createUserDTO.getEmail())).thenReturn(null);

		UserDTO respuesta = userService.create(createUserDTO);
		assertNotNull(respuesta);
	}

	@Test
	void create_email_alreadyRegister() {
		when(userRepository.findByEmail(createUserDTO.getEmail())).thenReturn(new UserModel());

		Assertions.assertThrows(UserEmailAlreadyRegisterException.class, () -> {
			userService.create(createUserDTO);
		});

	}

	@Test
	void getUsers_ok() {
		when(userRepository.findAll()).thenReturn(userDTOList);

		List<UserDTO> respuesta = userService.getUsers();
		assertNotNull(respuesta);
	}
}
