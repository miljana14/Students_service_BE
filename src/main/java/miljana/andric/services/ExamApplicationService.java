package miljana.andric.services;

import java.util.List;

import miljana.andric.dtos.ExamApplicationResponseDto;
import miljana.andric.entities.ExamApplicationEntity;
import miljana.andric.entities.ExamEntity;
import miljana.andric.exceptions.EngineeringException;

public interface ExamApplicationService {

	public ExamApplicationResponseDto enrollExams(List<ExamEntity> examsIds) throws EngineeringException;
	
	public List<ExamApplicationEntity> getAll();
	
}
