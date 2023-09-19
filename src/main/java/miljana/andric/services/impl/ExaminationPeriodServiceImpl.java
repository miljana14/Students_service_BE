package miljana.andric.services.impl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import miljana.andric.dtos.ExaminationPeriodDto;
import miljana.andric.dtos.ExaminationPeriodUpdateDto;
import miljana.andric.entities.ExamEntity;
import miljana.andric.entities.ExaminationPeriod;
import miljana.andric.exceptions.EngineeringException;
import miljana.andric.mappers.ExaminationPeriodMapper;
import miljana.andric.repositories.ExamRepository;
import miljana.andric.repositories.ExaminationPeriodRepository;
import miljana.andric.services.ExaminationPeriodService;

@Service
public class ExaminationPeriodServiceImpl implements ExaminationPeriodService {

	@Autowired
	private ExaminationPeriodRepository examinationPeriodRepository;
	@Autowired
	private ExaminationPeriodMapper examinationPeriodMapper;
	@Autowired
	private ExamRepository examRepository;

	@Override
	public List<ExaminationPeriod> getAllExaminationPeriods() {
		return examinationPeriodRepository.findAll().stream().collect(Collectors.toList());
	}
	
	@Override
	public List<ExaminationPeriod> getAllExaminationPeriodsPresentOrFuture() {
		LocalDate today = LocalDate.now();
		List<ExaminationPeriod> newList = examinationPeriodRepository.findAll().stream()
				.filter(examinationPeriod -> examinationPeriod.getBeginDate().isAfter(today) || examinationPeriod.getBeginDate().isEqual(today))
				.collect(Collectors.toList());
		
		return newList;
	}

	@Override
	public ExaminationPeriodDto findByName(String name) throws EngineeringException {
		Optional<ExaminationPeriod> examinationPeriod = examinationPeriodRepository.findByName(name);
		if (!examinationPeriod.isPresent()) {
			throw new EngineeringException(404, "Not found examination period with that name");
		}
		return examinationPeriodMapper.toDto(examinationPeriod.get());
	}

	@Override
	public ExaminationPeriod findById(Long id) throws EngineeringException {
		Optional<ExaminationPeriod> examinationPeriod = examinationPeriodRepository.findById(id);
		if (!examinationPeriod.isPresent()) {
			throw new EngineeringException(404, "Not found examination period with that id");
		}
		return examinationPeriod.get();
	}

	@Override
	public ExaminationPeriodDto addExaminationPeriod(ExaminationPeriodDto examinationPeriod)
			throws EngineeringException {

		if(examinationPeriodRepository.existsByName(examinationPeriod.getName())) {
			throw new EngineeringException(404, "Already exists examination period with that name");
		}
		if (examinationPeriodRepository.existsByActiveTrue() && examinationPeriod.isActive()) {
			throw new EngineeringException(404, "Already exists active examination period");
		}

		if (examinationPeriod.getBeginDate().isAfter(examinationPeriod.getEndDate())) {
			throw new EngineeringException(400, "Begin date must to be before end date");
		}

		if (examinationPeriodRepository.existsByBeginDateLessThanEqualAndEndDateGreaterThanEqual(
				examinationPeriod.getEndDate(), examinationPeriod.getBeginDate())) {
			throw new EngineeringException(403, "Examination period dates overlap with existing period");
		}

		ExaminationPeriod ep = examinationPeriodMapper.toEntity(examinationPeriod);
		ep.setActive(false);
		examinationPeriodRepository.save(ep);

		return examinationPeriodMapper.toDto(ep);

	}

	@Override
	public Page<ExaminationPeriod> findAll(Integer pageNo, Integer pageSize, String sortBy, String sortOrder) {
		Sort.Direction direction = "asc".equalsIgnoreCase(sortOrder) ? Sort.Direction.ASC : Sort.Direction.DESC;
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, sortBy));
		Page<ExaminationPeriod> entites = examinationPeriodRepository.findAll(pageable);
		return entites;
	}

	@Override
	public ExaminationPeriodDto editExaminationPeriod(Long id, ExaminationPeriodUpdateDto examinationPeriod)
			throws EngineeringException {
		ExaminationPeriod existingPeriod = examinationPeriodRepository.findById(id)
				.orElseThrow(() -> new EngineeringException(404, "Examination period not found"));

		if (examinationPeriod.isActive() && examinationPeriodRepository.existsByActiveTrue()) {
			existingPeriod.setActive(true);
			ExaminationPeriod activePeriod = examinationPeriodRepository.findByActiveTrue().get();
			activePeriod.setActive(false);
		}

		existingPeriod.setActive(examinationPeriod.isActive());

		ExaminationPeriod updatedPeriod = examinationPeriodRepository.save(existingPeriod);
		return examinationPeriodMapper.toDto(updatedPeriod);
	}

	@Override
	public void delete(Long id) throws EngineeringException {
		if (examinationPeriodRepository.findById(id).isEmpty()) {
			throw new EngineeringException(404, "Examination period is not found");
		}
		examinationPeriodRepository.deleteById(id);
	}
	
	@Override
	 public void deleteExaminationPeriodsFromPast() {
	        List<ExaminationPeriod> examinationPeriods = examinationPeriodRepository.findAll();
	        LocalDate today = LocalDate.now();

	        for (ExaminationPeriod examinationPeriod : examinationPeriods) {
	            if (examinationPeriod.getEndDate().isBefore(today)) {
	            	examinationPeriodRepository.delete(examinationPeriod);
	            }
	        }
	    }

	@Override
	public ExaminationPeriod addExaminationPeriodByClone(String name, LocalDate beginDate, String cloneName)
			throws EngineeringException {
		if(!examinationPeriodRepository.existsByName(cloneName)) {
			throw new EngineeringException(404, "Examination period with that name does not exist");
		}
		ExaminationPeriod examinationPeriod = new ExaminationPeriod();
		ExaminationPeriod epClone = examinationPeriodRepository.findByName(cloneName).get();
		
		Long days = ChronoUnit.DAYS.between(epClone.getBeginDate(), epClone.getEndDate());

		examinationPeriod.setName(name);
		examinationPeriod.setBeginDate(beginDate);
		examinationPeriod.setEndDate(beginDate.plusDays(days));
		examinationPeriod.setActive(false);
		examinationPeriodRepository.save(examinationPeriod);
		
		List<ExamEntity> exams = examRepository.findByExaminationPeriod(epClone);
		List<ExamEntity> newExams = new ArrayList<>();
		
		for (int i = 0; i < exams.size(); i++) {
		    ExamEntity exam = exams.get(i);
		    Long daysFromBegin = ChronoUnit.DAYS.between(epClone.getBeginDate(), exam.getExamDate());
		    LocalDate newExamDate = beginDate.plusDays(daysFromBegin);
		    ExamEntity newExam = new ExamEntity(examinationPeriod, exam.getSubject(), exam.getProfessor(), newExamDate);
		    newExams.add(newExam);
		}
		
		examRepository.saveAll(newExams);

		return examinationPeriod;
	}
	
	@Override
	public Page<ExaminationPeriod> search(Integer pageNo, Integer pageSize, String sortBy, String sortOrder, 
            String keyword, boolean name, boolean beginDate, boolean endDate, boolean active) {
        
        List<ExaminationPeriod> examinationPeriodEntities = examinationPeriodRepository.findAll();
        List<ExaminationPeriod> examinationPeriods = new ArrayList<>();

        if ((keyword != null && !keyword.isEmpty()) &&
            (name == false && beginDate == false && endDate == false && active == false)) {

            examinationPeriods.addAll(examinationPeriodEntities.stream()
                .filter(period -> 
                    period.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                    period.getBeginDate().toString().toLowerCase().contains(keyword.toLowerCase()) ||
                    period.getEndDate().toString().toLowerCase().contains(keyword.toLowerCase()) ||
                    String.valueOf(period.isActive()).toLowerCase().equals(keyword.toLowerCase()))
                .collect(Collectors.toList()));
        }

        if (name) {
            examinationPeriods.addAll(examinationPeriodEntities.stream()
                .filter(period -> period.getName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList()));
        }

        if (beginDate) {
            examinationPeriods.addAll(examinationPeriodEntities.stream()
                .filter(period -> period.getBeginDate().toString().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList()));
        }

        if (endDate) {
            examinationPeriods.addAll(examinationPeriodEntities.stream()
                .filter(period -> period.getEndDate().toString().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList()));
        }

        if (active) {
            examinationPeriods.addAll(examinationPeriodEntities.stream()
                .filter(period -> String.valueOf(period.isActive()).toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList()));
        }

        examinationPeriods.sort((s1, s2) -> {
	    	 int compareResult = 0;

	         if ("asc".equalsIgnoreCase(sortOrder)) {
	             if ("name".equalsIgnoreCase(sortBy)) {
	                 compareResult = s1.getName().compareTo(s2.getName());
	             }
	         } else if ("desc".equalsIgnoreCase(sortOrder)) {
	             if ("name".equalsIgnoreCase(sortBy)) {
	                 compareResult = s2.getName().compareTo(s1.getName());
	             }
	         }
	        return compareResult != 0 ? compareResult : s1.getId().compareTo(s2.getId());
	    });

	    int totalElements = examinationPeriods.size();

	    int fromIndex = pageNo * pageSize;
	    int toIndex = Math.min(fromIndex + pageSize, totalElements);

	    List<ExaminationPeriod> pagedExaminationPeriods = examinationPeriods.subList(fromIndex, toIndex);

	    Pageable pageable = PageRequest.of(pageNo , pageSize, Sort.by(sortOrder, sortBy));
	    return new PageImpl<>(pagedExaminationPeriods, pageable, totalElements);
    }

}
