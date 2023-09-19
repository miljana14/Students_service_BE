package miljana.andric.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import miljana.andric.dtos.ProfessorResponseDto;
import miljana.andric.entities.ProfessorEntity;
import miljana.andric.entities.UserEntity;
import miljana.andric.repositories.UserRepository;

@Component
public class ProfessorMapper implements GenericMapper<ProfessorEntity, ProfessorResponseDto>{

	@Autowired
	UserRepository userRepository;
	
	@Override
	public ProfessorEntity toEntity(ProfessorResponseDto dto) {
		ProfessorEntity professor = new ProfessorEntity();
		professor.setId(dto.getId());
		professor.setFirstName(dto.getFirstName());
		professor.setLastName(dto.getLastName());
		professor.setEmail(dto.getEmail());
		professor.setAddress(dto.getAddress());
		professor.setPostalCode(dto.getPostalCode());
		professor.setPhone(dto.getPhone());
		professor.setReelectionDate(dto.getReelectionDate());
		professor.setTitle(dto.getTitle());
		return professor;
	}

	@Override
	public ProfessorResponseDto toDto(ProfessorEntity entity) {
		ProfessorResponseDto professor = new ProfessorResponseDto();
		professor.setId(entity.getId());
		professor.setFirstName(entity.getFirstName());
		professor.setLastName(entity.getLastName());
		professor.setEmail(entity.getEmail());
		professor.setAddress(entity.getAddress());
		professor.setPostalCode(entity.getPostalCode());
		professor.setPhone(entity.getPhone());
		professor.setReelectionDate(entity.getReelectionDate());
		professor.setTitle(entity.getTitle());
		for(UserEntity user : userRepository.findAll()) {
			if(user.getFirstName().equals(entity.getFirstName()) && user.getLastName().equals(entity.getLastName())){
				professor.setUsername(user.getUsername());
				professor.setPassword(user.getPassword());	
			}
		}
		return professor;
	}

}
