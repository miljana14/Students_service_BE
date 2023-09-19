package miljana.andric.mappers;

import org.springframework.stereotype.Component;

import miljana.andric.dtos.SubjectDto;
import miljana.andric.entities.SubjectEntity;

@Component
public class SubjectMapper implements GenericMapper<SubjectEntity, SubjectDto>{

	@Override
	public SubjectEntity toEntity(SubjectDto dto) {
		return new SubjectEntity(dto.getName(),dto.getDescription(),dto.getNoOfESP(),dto.getYearOfStudy(),dto.getSemester());
	}

	@Override
	public SubjectDto toDto(SubjectEntity entity) {
		return new SubjectDto(entity.getName(),entity.getDescription(),entity.getNoOfESP(),entity.getYearOfStudy(),entity.getSemester());
	}

}
