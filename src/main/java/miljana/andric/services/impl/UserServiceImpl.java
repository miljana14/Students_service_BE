package miljana.andric.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import miljana.andric.dtos.UserRegisterDto;
import miljana.andric.dtos.UserResponseDto;
import miljana.andric.dtos.UserUpdateDto;
import miljana.andric.entities.RoleEnum;
import miljana.andric.entities.UserEntity;
import miljana.andric.exceptions.EngineeringException;
import miljana.andric.mappers.UserMapper;
import miljana.andric.repositories.UserRepository;
import miljana.andric.services.UserService;
import miljana.andric.services.security.MyUserDetails;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserMapper userMapper;

	@Override
	public List<UserEntity> findAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public List<UserResponseDto> findUsersByPage(int number) {
		Page<UserEntity> page = userRepository.findAll(
				PageRequest.of(number + 1, 2, Sort.by(Sort.Direction.ASC, "id")));
		return page.stream().map(userMapper::toDto).collect(Collectors.toList());
	}

	@Override
	public Optional<UserResponseDto> findById(String username) throws EngineeringException {
		Optional<UserEntity> userEntity = userRepository.findById(username);
		return userEntity.map(userMapper::toDto);
	}

	@Override
	public UserResponseDto addUser(UserRegisterDto userRegister) throws EngineeringException {
		UserEntity user = new UserEntity();
		user.setFirstName(userRegister.getFirstName());
		user.setLastName(userRegister.getLastName());
		if(userRepository.findByEmail(userRegister.getEmail()).isPresent()) {
			throw new EngineeringException(1, "Email must be unique");
		}
		user.setEmail(userRegister.getEmail());
		if(userRepository.findById(userRegister.getUsername()).isPresent()) {
			throw new EngineeringException(1, "Username must be unique");
		}
		user.setUsername(userRegister.getUsername());
		user.setPassword(userRegister.getPassword());
		user.setRole(RoleEnum.ROLE_ADMIN);
		user.setNewUser(true);
	
		userRepository.save(user);
		return userMapper.toDto(user);
	}

	@Override
	public void deleteUser(String username) throws EngineeringException {
		Optional<UserEntity> userEntity = userRepository.findById(username);
		if(userEntity.isEmpty()) {
			throw new EngineeringException(404,"Not found");
		}
		userRepository.delete(userEntity.get());
		
	}

	@Override
	public UserResponseDto editUser(String username, UserUpdateDto userRegister) throws EngineeringException {
		
		userRepository.findById(username).ifPresent(user -> {
			user.setFirstName(Optional.ofNullable(userRegister.getFirstName()).orElse(user.getFirstName())); 
			user.setLastName(Optional.ofNullable(userRegister.getLastName()).orElse(user.getLastName())); 
			user.setPassword(Optional.ofNullable(userRegister.getPassword()).orElse(user.getPassword())); 
			user.setRole(Optional.ofNullable(RoleEnum.ROLE_ADMIN).orElse(user.getRole())); 
			userRepository.save(user);
		});
		
		Optional<UserEntity> updatedUser = userRepository.findById(username);
		
		if (updatedUser.isPresent()) {
			return userMapper.toDto(updatedUser.get());
		} else {
			throw new EngineeringException(404,"User not found with username: " + username);
		}
		
	}

	@Override
	public Page<UserResponseDto> findAll(Integer pageNo, Integer pageSize, String sortBy, String sortOrder) {
		Sort.Direction direction = "asc".equalsIgnoreCase(sortOrder) ? Sort.Direction.ASC : Sort.Direction.DESC;
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, sortBy));
		Page<UserResponseDto> entites = userRepository.findAll(pageable).map(userMapper::toDto);
		return entites;
	}

	@Override
	public List<UserResponseDto> search(String keyword) {
		List<UserEntity> userEntities = userRepository.search(keyword);
		return userEntities.stream().map(userMapper::toDto).collect(Collectors.toList());
	}
	
	@Override
	public MyUserDetails changePassword(String password, String repeatedPassword) throws EngineeringException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		MyUserDetails user = (MyUserDetails) auth.getPrincipal();
		UserEntity student = userRepository.findById(user.getUsername()).get();
		if(!password.equals(repeatedPassword)) {
			throw new EngineeringException(404,"Passwords doesn`t match");
		}
		student.setPassword(password);
		user.setNewUser(false);
		student.setNewUser(false);
		userRepository.save(student);
		return user;
	}

}
