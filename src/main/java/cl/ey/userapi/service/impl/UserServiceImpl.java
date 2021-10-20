package cl.ey.userapi.service.impl;

import cl.ey.userapi.exception.UserEmailAlreadyRegisterException;
import cl.ey.userapi.util.JwtTokenUtil;
import cl.ey.userapi.dto.CreateUserDTO;
import cl.ey.userapi.dto.PhoneDTO;
import cl.ey.userapi.dto.UserDTO;
import cl.ey.userapi.model.PhoneModel;
import cl.ey.userapi.model.UserModel;
import cl.ey.userapi.repository.UserRepository;
import cl.ey.userapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	public UserDTO create(CreateUserDTO createUserDTO) throws UserEmailAlreadyRegisterException {

		UserModel userModel = userRepository.findByEmail(createUserDTO.getEmail());
		if(userModel != null){
			throw new UserEmailAlreadyRegisterException("User email ["+createUserDTO.getEmail()+"] already register");
		}

		String encodedPassword = passwordEncoder.encode(createUserDTO.getPassword());

		List<PhoneModel> phones = new ArrayList<>();
		if(createUserDTO.getPhones() != null && !createUserDTO.getPhones().isEmpty()){
			for (PhoneDTO phoneDto : createUserDTO.getPhones()) {
				phones.add(new PhoneModel(phoneDto.getNumber(),phoneDto.getCitycode(),phoneDto.getContrycode()));
			}
		}

		userModel = new UserModel();
		userModel.setActive(true);
		userModel.setEmail(createUserDTO.getEmail());
		userModel.setName(createUserDTO.getName());
		userModel.setPassword(encodedPassword);
		userModel.setUuid(UUID.randomUUID());
		userModel.setToken(jwtTokenUtil.generateToken(createUserDTO.getEmail()));
		userModel.setPhones(phones);
		userModel.setLastLogin(new Date());

		userRepository.save(userModel);

		return generateUserDto(userModel);
	}


	@Override
	public List<UserDTO> getUsers() {
		List<UserModel> userModelList = userRepository.findAll();
		List<UserDTO> userDTOList = new ArrayList<>();
		userModelList.forEach( userModel -> userDTOList.add(generateUserDto(userModel)));
		return userDTOList;
	}

	private UserDTO generateUserDto(UserModel userModel) {
		UserDTO userDTO = new UserDTO();
		userDTO.setActive(userModel.getActive());
		userDTO.setCreated(userModel.getCreatedAt());
		userDTO.setLastLogin(userModel.getLastLogin());
		userDTO.setModified(userModel.getUpdatedAt());
		userDTO.setToken(userModel.getToken());
		userDTO.setId(userModel.getUuid().toString());
		return userDTO;
	}
}
