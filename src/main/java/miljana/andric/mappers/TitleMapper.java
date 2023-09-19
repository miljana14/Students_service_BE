package miljana.andric.mappers;

import org.springframework.stereotype.Component;

import miljana.andric.dtos.TitleDto;
import miljana.andric.entities.TitleEntity;

@Component
public class TitleMapper implements GenericMapper<TitleEntity, TitleDto>{

	@Override
	public TitleEntity toEntity(TitleDto dto) {
		return new TitleEntity(dto.getTitle());
	}

	@Override
	public TitleDto toDto(TitleEntity entity) {
		return new TitleDto(entity.getTitle());
	}

}
