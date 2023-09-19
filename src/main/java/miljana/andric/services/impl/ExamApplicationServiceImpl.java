package miljana.andric.services.impl;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import miljana.andric.dtos.ExamApplicationResponseDto;
import miljana.andric.entities.ExamApplicationEntity;
import miljana.andric.entities.ExamEntity;
import miljana.andric.entities.ExaminationPeriod;
import miljana.andric.entities.MarkEntity;
import miljana.andric.entities.StudentEntity;
import miljana.andric.entities.UserEntity;
import miljana.andric.exceptions.EngineeringException;
import miljana.andric.repositories.ExamApplicationRepository;
import miljana.andric.repositories.ExaminationPeriodRepository;
import miljana.andric.repositories.MarkRepository;
import miljana.andric.repositories.StudentRepository;
import miljana.andric.repositories.UserRepository;
import miljana.andric.services.ExamApplicationService;

@Service
public class ExamApplicationServiceImpl implements ExamApplicationService{
	
	@Autowired
	private ExamApplicationRepository examApplicationRepository;
	@Autowired
	private ExaminationPeriodRepository examinationPeriodRepository;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private MarkRepository markRepository;
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public ExamApplicationResponseDto enrollExams(List<ExamEntity> exams) throws EngineeringException {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		boolean isStudent = auth.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_STUDENT")); 

		if(auth == null || !isStudent) {
			 throw new EngineeringException(403, "Unauthorized");
		}
		String username = auth.getName(); 
		UserEntity user = userRepository.findById(username).get();
		
		StudentEntity student = studentRepository.findByEmail(user.getEmail()).get();
		List<MarkEntity> marks = markRepository.findByStudent(student);
		
//	    LocalDate today =  LocalDate.now();
		LocalDate today = LocalDate.of(2023,7,26);
	   
	    ExaminationPeriod activePeriod = examinationPeriodRepository.findByActiveTrue().get();
	    if (activePeriod == null || !activePeriod.getBeginDate().minusWeeks(1).datesUntil(activePeriod.getBeginDate()).anyMatch(today::equals)) {
	        throw new EngineeringException(400,"Enrollment is not possible at the moment.");
	    }

	    for (ExamEntity exam : exams) {
	        if (exam.getSubject().getYearOfStudy() > student.getCurrentYearOfStudy()) {
	            throw new EngineeringException(400,"Enrollment not possible - student's study year is lower than required.");
	        }
	        if(marks.stream().anyMatch(m -> m.getExam().equals(exam) && m.getMark() > 5)) {
	        	throw new EngineeringException(400,"Enrollment not possible - student already passed the exam.");
	        }
	    }
	    
	    ExamApplicationEntity application = new ExamApplicationEntity();
	    application.setDate(today);
	    application.setExams(new HashSet<>(exams));
	    application.setStudent(student);
	    examApplicationRepository.save(application);

	    return new ExamApplicationResponseDto(student, exams);
	    
	}

	@Override
	public List<ExamApplicationEntity> getAll() {
		return examApplicationRepository.findAll();
	}
	
}
