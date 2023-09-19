package miljana.andric.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import miljana.andric.dtos.TitleDto;
import miljana.andric.entities.TitleEntity;
import miljana.andric.entities.TitleEnum;
import miljana.andric.exceptions.EngineeringException;
import miljana.andric.mappers.TitleMapper;
import miljana.andric.repositories.TitleRepository;
import miljana.andric.services.TitleService;

@Service
public class TitleServiceImpl implements TitleService{
	
	@Autowired
	private TitleRepository titleRepository;
	@Autowired
	TitleMapper titleMapper;

	@Override
	public List<TitleEntity> getAllTitles() {
		return titleRepository.findAll().stream().collect(Collectors.toList());
	}

	@Override
	public Optional<TitleDto> findById(Long id) throws EngineeringException {
		Optional<TitleEntity> title = titleRepository.findById(id);
        if (!title.isPresent()) {
            throw new EngineeringException(404, "Not found");
        }
        return Optional.of(titleMapper.toDto(title.get()));
	}

	@Override
	public Optional<TitleDto> findByTitle(TitleEnum title) throws EngineeringException {
		Optional<TitleEntity> titleEntity = titleRepository.findByTitle(title);
        if (!titleEntity.isPresent()) {
            throw new EngineeringException(404, "Not found");
        }
        return Optional.of(titleMapper.toDto(titleEntity.get()));
	}

}
