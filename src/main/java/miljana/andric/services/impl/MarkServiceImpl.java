package miljana.andric.services.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import miljana.andric.dtos.MarkDto;
import miljana.andric.entities.ExamEntity;
import miljana.andric.entities.Index;
import miljana.andric.entities.MarkEntity;
import miljana.andric.entities.ProfessorEntity;
import miljana.andric.entities.StudentEntity;
import miljana.andric.entities.UserEntity;
import miljana.andric.exceptions.EngineeringException;
import miljana.andric.repositories.ExamApplicationRepository;
import miljana.andric.repositories.ExamRepository;
import miljana.andric.repositories.MarkRepository;
import miljana.andric.repositories.ProfessorRepository;
import miljana.andric.repositories.StudentRepository;
import miljana.andric.repositories.UserRepository;
import miljana.andric.services.MarkService;

@Service
public class MarkServiceImpl implements MarkService {

	@Autowired
	private MarkRepository markRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ProfessorRepository professorRepository;
	@Autowired
	private ExamRepository examRepository;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private ExamApplicationRepository examApplicationRepository;

	@Override
	public MarkDto addMark(Long examId, String index, Integer mark) throws EngineeringException {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		boolean isProfessor = auth.getAuthorities().stream()
				.anyMatch(role -> role.getAuthority().equals("ROLE_PROFESSOR"));

		if (auth == null || !isProfessor) {
			throw new EngineeringException(403, "Unauthorized");
		}

		String username = auth.getName();
		UserEntity user = userRepository.findById(username).get();
		
		String[] parts = index.split("/");
		String number = parts[0];
		String year = parts[1];

		ProfessorEntity professor = professorRepository.findByEmail(user.getEmail()).get();
		ExamEntity exam = examRepository.findById(examId).orElseThrow(() -> new EngineeringException(404, "Exam not found"));
		StudentEntity student = studentRepository.findById(new Index(number, Long.parseLong(year))).orElseThrow(() -> new EngineeringException(404, "Student not found"));
		
//		LocalDate currentDate = LocalDate.now();
		LocalDate currentDate = LocalDate.of(2023, 8, 27);
		
		if (!professor.getExams().contains(exam)) {
			throw new EngineeringException(403, "You are not authorized to add mark for this exam");
		}

		if (!examApplicationRepository.findByStudent(student).stream().flatMap(application -> application.getExams().stream()).collect(Collectors.toSet()).contains(exam)) {
			throw new EngineeringException(403, "Student didn`t apply this exam");
		}
		
		if (currentDate.isBefore(exam.getExamDate())) {
			throw new EngineeringException(400, "Can not insert mark for this exam at the moment");
		}
		
		if(mark != 0 && mark != 5 && mark != 6 && mark != 7 && mark != 8 && mark != 9 && mark != 10) {
			throw new EngineeringException(400, "Invalid mark");
		}

		MarkEntity markEntity = new MarkEntity();
		markEntity.setStudent(student);
		markEntity.setExam(exam);
		markEntity.setMark(mark);

		markRepository.save(markEntity);
		
		MarkDto markDto = new MarkDto();
		markDto.setStudent(student.getFirstName() + " " + student.getLastName());
		markDto.setSubject(exam.getSubject().getName());
		markDto.setMark(mark);

		return markDto;
	}

	@Override
	public List<MarkEntity> marksForStudent() throws EngineeringException{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		boolean isStudent = auth.getAuthorities().stream()
				.anyMatch(role -> role.getAuthority().equals("ROLE_STUDENT"));

		if (auth == null || !isStudent) {
			throw new EngineeringException(403, "Unauthorized");
		}
		
		String username = auth.getName();
		UserEntity user = userRepository.findById(username).get();
		StudentEntity student = studentRepository.findByEmail(user.getEmail()).get();
		
		List<MarkEntity> marksForStudent = markRepository.findByStudent(student);
		return marksForStudent;
	}
	
	@Override
	public List<MarkEntity> getAll(){
		return markRepository.findAll();
	}

}
