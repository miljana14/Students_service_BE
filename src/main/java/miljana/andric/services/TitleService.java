package miljana.andric.services;

import java.util.List;
import java.util.Optional;

import miljana.andric.dtos.TitleDto;
import miljana.andric.entities.TitleEntity;
import miljana.andric.entities.TitleEnum;
import miljana.andric.exceptions.EngineeringException;

public interface TitleService {
	
	List<TitleEntity> getAllTitles();
	
	Optional<TitleDto> findById(Long id) throws EngineeringException;
	
	Optional<TitleDto> findByTitle(TitleEnum title) throws EngineeringException;

}
