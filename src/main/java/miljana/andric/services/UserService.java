package miljana.andric.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import miljana.andric.dtos.UserRegisterDto;
import miljana.andric.dtos.UserResponseDto;
import miljana.andric.dtos.UserUpdateDto;
import miljana.andric.entities.UserEntity;
import miljana.andric.exceptions.EngineeringException;
import miljana.andric.services.security.MyUserDetails;

public interface UserService {
	
	List<UserEntity> findAllUsers();
 	
 	List<UserResponseDto> findUsersByPage(int number);

    Optional<UserResponseDto> findById(String username) throws EngineeringException;

    UserResponseDto addUser(UserRegisterDto user) throws EngineeringException;

    void deleteUser(String username) throws EngineeringException;

    UserResponseDto editUser(String username, UserUpdateDto userRegister) throws EngineeringException;
    
    Page<UserResponseDto> findAll(Integer pageNo, Integer pageSize, String sortBy, String sortOrder);
    
    List<UserResponseDto> search(String keyword);
    
    MyUserDetails changePassword(String password, String repeatedPassword) throws EngineeringException;

}
