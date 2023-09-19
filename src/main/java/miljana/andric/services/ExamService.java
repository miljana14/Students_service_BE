package miljana.andric.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import miljana.andric.dtos.ExamDto;
import miljana.andric.entities.ExamEntity;
import miljana.andric.exceptions.EngineeringException;

public interface ExamService {

	List<ExamEntity> getAllExams();
	
	Optional<ExamEntity> findById(Long id) throws EngineeringException;
	
	ExamDto addExam(ExamEntity exam) throws EngineeringException;
	
	void deleteExam(Long id) throws EngineeringException;
	
	ExamEntity editExam(Long id, ExamEntity exam) throws EngineeringException;
	
	Page<ExamEntity> findAll(Integer pageNo, Integer pageSize, String sortBy, String sortOrder);
	
	List<ExamEntity> findByExaminationPeriod(Long examinationPeriod);

	List<LocalDate> availableDates(Long examinationPeriodId);
	
	List<ExamEntity> getAvailableExamsForStudent() throws EngineeringException;
	
	List<ExamEntity> getAvailableExamsForProfessor() throws EngineeringException;
	
	Page<ExamEntity> search(Integer pageNo, Integer pageSize, String sortBy, String sortOrder, String keyword, boolean examinationPeriod, boolean subject, boolean professor, boolean examDate);
	
}
