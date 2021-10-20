package cl.ey.userapi.service.security;

import cl.ey.userapi.model.UserModel;
import cl.ey.userapi.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtUserDetailsServiceImplTest {

	@InjectMocks
	JwtUserDetailsServiceImpl jwtUserDetailsService;

	@Mock
	private UserRepository userRepository;

	String username;
	UserModel userModel;

	@BeforeEach
	void init() {
		username = "username";
		userModel = new UserModel();
		userModel.setEmail("email");
		userModel.setPassword("pass");
	}

	@Test
	void loadUserByUsername_ok(){
		when(userRepository.findByEmail(username)).thenReturn(userModel);

		UserDetails respuesta = jwtUserDetailsService.loadUserByUsername(username);
		assertNotNull(respuesta);
	}

	@Test
	void loadUserByUsername_username_notFound(){
		when(userRepository.findByEmail(username)).thenReturn(null);

		Assertions.assertThrows(UsernameNotFoundException.class, () -> {
			jwtUserDetailsService.loadUserByUsername(username);
		});
	}
}
