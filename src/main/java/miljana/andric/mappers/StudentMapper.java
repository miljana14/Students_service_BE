package miljana.andric.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import miljana.andric.dtos.StudentResponseDto;
import miljana.andric.entities.StudentEntity;
import miljana.andric.entities.UserEntity;
import miljana.andric.repositories.UserRepository;

@Component
public class StudentMapper implements GenericMapper<StudentEntity, StudentResponseDto>{
	
	@Autowired
	UserRepository userRepository;

	@Override
	public StudentEntity toEntity(StudentResponseDto dto) {
		StudentEntity student = new StudentEntity();
		student.setIndexNumber(dto.getIndexNumber());
		student.setIndexYear(dto.getIndexYear());
		student.setFirstName(dto.getFirstName());
		student.setLastName(dto.getLastName());
		student.setEmail(dto.getEmail());
		student.setAddress(dto.getAddress());
		student.setPostalCode(dto.getPostalCode());
		student.setCurrentYearOfStudy(dto.getCurrentYearOfStudy());
		return student;
	}

	@Override
	public StudentResponseDto toDto(StudentEntity entity) {
		StudentResponseDto student = new StudentResponseDto();
		student.setIndexNumber(entity.getIndexNumber());
		student.setIndexYear(entity.getIndexYear());
		student.setFirstName(entity.getFirstName());
		student.setLastName(entity.getLastName());
		student.setEmail(entity.getEmail());
		student.setAddress(entity.getAddress());
		student.setPostalCode(entity.getPostalCode());
		student.setCurrentYearOfStudy(entity.getCurrentYearOfStudy());
		for(UserEntity user : userRepository.findAll()) {
			if(user.getFirstName().equals(entity.getFirstName()) && user.getLastName().equals(entity.getLastName())){
				student.setUsername(user.getUsername());
				student.setPassword(user.getPassword());	
			}
		}
		return student;
	}

}
