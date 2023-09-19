package miljana.andric.mappers;

import org.springframework.stereotype.Component;

import miljana.andric.dtos.CityDto;
import miljana.andric.entities.CityEntity;

@Component
public class CityMapper implements GenericMapper<CityEntity, CityDto>{

	@Override
	public CityEntity toEntity(CityDto dto) {
		return new CityEntity(dto.getPostalCode(),dto.getName());
	}

	@Override
	public CityDto toDto(CityEntity entity) {
		return new CityDto(entity.getPostalCode(), entity.getName());
	}

}
