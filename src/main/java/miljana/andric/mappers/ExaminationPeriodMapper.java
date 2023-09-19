package miljana.andric.mappers;

import org.springframework.stereotype.Component;

import miljana.andric.dtos.ExaminationPeriodDto;
import miljana.andric.entities.ExaminationPeriod;

@Component
public class ExaminationPeriodMapper implements GenericMapper<ExaminationPeriod, ExaminationPeriodDto>{

	@Override
	public ExaminationPeriod toEntity(ExaminationPeriodDto dto) {
		return new ExaminationPeriod(dto.getName(), dto.getBeginDate(), dto.getEndDate(), dto.isActive());
	}

	@Override
	public ExaminationPeriodDto toDto(ExaminationPeriod entity) {
		return new ExaminationPeriodDto(entity.getName(), entity.getBeginDate(), entity.getEndDate(), entity.isActive());
	}

}
