package miljana.andric.mappers;

import org.springframework.stereotype.Component;

import miljana.andric.dtos.ExamDto;
import miljana.andric.entities.ExamEntity;

@Component
public class ExamMapper implements GenericMapper<ExamEntity, ExamDto>{

	@Override
	public ExamEntity toEntity(ExamDto dto) {
		return new ExamEntity(dto.getExaminationPeriod(), dto.getSubject(), dto.getProfessor(), dto.getExamDate());
	}

	@Override
	public ExamDto toDto(ExamEntity entity) {
		return new ExamDto(entity.getExaminationPeriod(), entity.getSubject(), entity.getProfessor(), entity.getExamDate());
	}
}
