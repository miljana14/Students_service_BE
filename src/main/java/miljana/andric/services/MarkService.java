package miljana.andric.services;

import java.util.List;

import miljana.andric.dtos.MarkDto;
import miljana.andric.entities.MarkEntity;
import miljana.andric.exceptions.EngineeringException;

public interface MarkService {
	
	public MarkDto addMark(Long examId, String index, Integer mark) throws EngineeringException;
	
	public List<MarkEntity> marksForStudent() throws EngineeringException;
	
	public List<MarkEntity> getAll();

}
