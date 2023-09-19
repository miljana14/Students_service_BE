package miljana.andric.mappers;

import miljana.andric.dtos.Dto;
import miljana.andric.entities.Entity;

public interface GenericMapper<E extends Entity, D extends Dto> {

    E toEntity(D dto);

    D toDto(E entity);
}