package cl.ey.userapi.service;

import cl.ey.userapi.dto.CreateUserDTO;
import cl.ey.userapi.dto.UserDTO;
import cl.ey.userapi.exception.UserEmailAlreadyRegisterException;

import java.util.List;

public interface UserService {
	UserDTO create(CreateUserDTO createUserDTO) throws UserEmailAlreadyRegisterException;

	List<UserDTO> getUsers();
}
