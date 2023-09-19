package miljana.andric.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;

import miljana.andric.dtos.ExaminationPeriodDto;
import miljana.andric.dtos.ExaminationPeriodUpdateDto;
import miljana.andric.entities.ExaminationPeriod;
import miljana.andric.exceptions.EngineeringException;

public interface ExaminationPeriodService {
	
	List<ExaminationPeriod> getAllExaminationPeriods();
	
	ExaminationPeriodDto findByName(String name) throws EngineeringException;
	
	ExaminationPeriod findById(Long id) throws EngineeringException;
	
	ExaminationPeriodDto addExaminationPeriod(ExaminationPeriodDto examinationPeriod) throws EngineeringException;
	
	Page<ExaminationPeriod> findAll(Integer pageNo, Integer pageSize, String sortBy, String sortOrder);
	
	ExaminationPeriodDto editExaminationPeriod(Long id, ExaminationPeriodUpdateDto examinationPeriod) throws EngineeringException;
	
	void delete(Long id) throws EngineeringException;
	
	void deleteExaminationPeriodsFromPast();
	
	ExaminationPeriod addExaminationPeriodByClone(String name, LocalDate beginDate, String cloneName) throws EngineeringException;
	
	Page<ExaminationPeriod> search(Integer pageNo, Integer pageSize, String sortBy, String sortOrder, String keyword, boolean name, boolean beginDate, boolean endDate, boolean active);
	
	List<ExaminationPeriod> getAllExaminationPeriodsPresentOrFuture();
}
