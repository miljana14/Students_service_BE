package miljana.andric.mappers;

import org.springframework.stereotype.Component;

import miljana.andric.dtos.UserResponseDto;
import miljana.andric.entities.UserEntity;

@Component
public class UserMapper implements GenericMapper<UserEntity, UserResponseDto>{

	@Override
	public UserEntity toEntity(UserResponseDto dto) {
		return new UserEntity(dto.getFirstname(), dto.getLastname(), dto.getEmail(), dto.getUsername());
	}

	@Override
	public UserResponseDto toDto(UserEntity entity) {
		return new UserResponseDto(entity.getFirstName(), entity.getLastName(), entity.getEmail(), entity.getUsername());
	}

}
