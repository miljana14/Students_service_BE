package miljana.andric.services.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import miljana.andric.dtos.ExamDto;
import miljana.andric.entities.ExamApplicationEntity;
import miljana.andric.entities.ExamEntity;
import miljana.andric.entities.ExaminationPeriod;
import miljana.andric.entities.MarkEntity;
import miljana.andric.entities.ProfessorEntity;
import miljana.andric.entities.StudentEntity;
import miljana.andric.entities.SubjectEntity;
import miljana.andric.entities.UserEntity;
import miljana.andric.exceptions.EngineeringException;
import miljana.andric.mappers.ExamMapper;
import miljana.andric.repositories.ExamApplicationRepository;
import miljana.andric.repositories.ExamRepository;
import miljana.andric.repositories.ExaminationPeriodRepository;
import miljana.andric.repositories.MarkRepository;
import miljana.andric.repositories.ProfessorRepository;
import miljana.andric.repositories.StudentRepository;
import miljana.andric.repositories.SubjectRepository;
import miljana.andric.repositories.UserRepository;
import miljana.andric.services.ExamService;

@Service
public class ExamServiceImpl implements ExamService {

	@Autowired
	private ExamRepository examRepository;
	@Autowired
	private SubjectRepository subjectRepository;
	@Autowired
	private ProfessorRepository professorRepository;
	@Autowired
	private ExaminationPeriodRepository examinationPeriodRepository;
	@Autowired
	private ExamMapper examMapper;
	@Autowired
	private MarkRepository markRepository;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ExamApplicationRepository examApplicationRepository;

	@Override
	public List<ExamEntity> getAllExams() {
		return examRepository.findAll();
	}

	@Override
	public Optional<ExamEntity> findById(Long id) throws EngineeringException {
		return examRepository.findById(id);
	}

	@Override
	public ExamDto addExam(ExamEntity exam) throws EngineeringException {
		SubjectEntity subject = subjectRepository.findById(exam.getSubject().getId())
				.orElseThrow(() -> new EngineeringException(400, "Subject not found"));

		ProfessorEntity professor = professorRepository.findById(exam.getProfessor().getId())
				.orElseThrow(() -> new EngineeringException(400, "Professor not found"));

		ExaminationPeriod examinationPeriod = examinationPeriodRepository.findById(exam.getExaminationPeriod().getId())
				.orElseThrow(() -> new EngineeringException(400, "Examination period not found"));

		if (!professor.getSubjects().contains(subject)) {
			throw new EngineeringException(400, "Proffesor does not teach that subject");
		}

		LocalDate examDate = exam.getExamDate();
		if (examRepository.existsBySubjectAndExaminationPeriod(subject, examinationPeriod)) {
			throw new EngineeringException(400, "Exam already exists for selected subject and examination period");
		}

		if (examDate.isBefore(examinationPeriod.getBeginDate()) || examDate.isAfter(examinationPeriod.getEndDate())) {
			throw new EngineeringException(400, "Exam date is not within the selected examination period");
		}
		ExamEntity examEntity = new ExamEntity(examinationPeriod, subject, professor, examDate);

		return examMapper.toDto(examRepository.save(examEntity));
	}

	@Override
	public void deleteExam(Long id) throws EngineeringException {
		Optional<ExamEntity> exam = examRepository.findById(id);
		if (exam.isEmpty()) {
			throw new EngineeringException(400, "Invalid entity" + exam);
		}
		examRepository.delete(exam.get());

	}

	@Override
	public ExamEntity editExam(Long id, ExamEntity exam) throws EngineeringException {
		ExamEntity examEntity = examRepository.findById(id)
				.orElseThrow(() -> new EngineeringException(404, "Exam not found"));

		SubjectEntity subject = subjectRepository.findById(exam.getSubject().getId())
				.orElseThrow(() -> new EngineeringException(400, "Subject not found"));

		ProfessorEntity professor = professorRepository.findById(exam.getProfessor().getId())
				.orElseThrow(() -> new EngineeringException(400, "Professor not found"));

		ExaminationPeriod examinationPeriod = examinationPeriodRepository.findById(exam.getExaminationPeriod().getId())
				.orElseThrow(() -> new EngineeringException(400, "Examination period not found"));

		if (exam.getExamDate().isBefore(examinationPeriod.getBeginDate())
				|| exam.getExamDate().isAfter(examinationPeriod.getEndDate())) {
			throw new EngineeringException(400, "Exam date is not within the selected examination period");
		}

		examEntity.setSubject(subject);
		examEntity.setProfessor(professor);
		examEntity.setExamDate(exam.getExamDate());

		return examRepository.save(examEntity);
	}

	@Override
	public Page<ExamEntity> findAll(Integer pageNo, Integer pageSize, String sortBy, String sortOrder) {
		Sort.Direction direction = "asc".equalsIgnoreCase(sortOrder) ? Sort.Direction.ASC : Sort.Direction.DESC;
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, sortBy));
		Page<ExamEntity> entites = examRepository.findAll(pageable);
		return entites;
	}

	@Override
	public List<ExamEntity> findByExaminationPeriod(Long examinationPeriod) {
		ExaminationPeriod ep = examinationPeriodRepository.findById(examinationPeriod).get();
		List<ExamEntity> entites = examRepository.findByExaminationPeriod(ep);
		return entites;
	}

	@Override
	public List<LocalDate> availableDates(Long examinationPeriodId) {
		ExaminationPeriod examinationPeriod = examinationPeriodRepository.findById(examinationPeriodId).get();
		List<LocalDate> dates = new ArrayList<>();
		LocalDate currentDate = examinationPeriod.getBeginDate();

		while (!currentDate.isAfter(examinationPeriod.getEndDate())) {
			dates.add(currentDate);
			currentDate = currentDate.plusDays(1);
		}

		return dates;
	}

	@Override
	public List<ExamEntity> getAvailableExamsForStudent() throws EngineeringException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.getAuthorities().stream().anyMatch(a -> !a.getAuthority().equals("ROLE_STUDENT"))) {
			throw new EngineeringException(401, "Unauthorized");
		}
		UserEntity user = userRepository.findById(auth.getName()).get();
		StudentEntity student = studentRepository.findByEmail(user.getEmail()).get();

		ExaminationPeriod activeExaminationPeriod = examinationPeriodRepository.findByActiveTrue().get();

		List<ExamEntity> examsPassedByStudent = markRepository.findByStudent(student).stream().map(MarkEntity::getExam)
				.collect(Collectors.toList());

		List<ExamEntity> allExams = examRepository.findAll();

		List<ExamApplicationEntity> allExamApplications = examApplicationRepository.findByStudent(student);

		List<ExamEntity> examsWithoutApplications = new ArrayList<ExamEntity>();
		Set<ExamEntity> exams = new HashSet<ExamEntity>();
		for (ExamApplicationEntity examApplication : allExamApplications) {
			exams.addAll(examApplication.getExams());

		}
		for (ExamEntity exam : allExams) {
			if (!exams.contains(exam)) {
				examsWithoutApplications.add(exam);
			}
		}

		List<ExamEntity> availableExamsByYearOfStudy = examsWithoutApplications.stream()
				.filter(exam -> exam.getSubject().getYearOfStudy() <= student.getCurrentYearOfStudy())
				.collect(Collectors.toList());

//		LocalDate now = LocalDate.now();
		LocalDate now = LocalDate.of(2023, 7, 26);

		List<ExamEntity> examsInActivePeriod = availableExamsByYearOfStudy.stream()
				.filter(exam -> exam.getExaminationPeriod().equals(activeExaminationPeriod))
				.filter(exam -> activeExaminationPeriod.getBeginDate().minusWeeks(1)
						.datesUntil(activeExaminationPeriod.getBeginDate()).anyMatch(now::equals))
				.collect(Collectors.toList());

		List<ExamEntity> availableExams = examsInActivePeriod.stream()
				.filter(exam -> !examsPassedByStudent.contains(exam)).collect(Collectors.toList());

		return availableExams;
	}

	@Override
	public List<ExamEntity> getAvailableExamsForProfessor() throws EngineeringException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.getAuthorities().stream().anyMatch(a -> !a.getAuthority().equals("ROLE_PROFESSOR"))) {
			throw new EngineeringException(401, "Unauthorized");
		}
		UserEntity user = userRepository.findById(auth.getName()).get();
		ProfessorEntity professor = professorRepository.findByEmail(user.getEmail()).get();

		List<ExamEntity> exams = professor.getExams();
//		LocalDate currentDate = LocalDate.now();
		LocalDate currentDate = LocalDate.of(2023, 8, 31);
		exams = exams.stream().filter(exam -> exam.getExamDate().isBefore(currentDate)).collect(Collectors.toList());
		
//		exams = exams.stream().filter(exam -> !markRepository.existsByExam(exam)).collect(Collectors.toList());
		return exams;
	}

	@Override
	public Page<ExamEntity> search(Integer pageNo, Integer pageSize, String sortBy, String sortOrder, String keyword, boolean examinationPeriod, boolean subject, boolean professor,
			boolean examDate) {

		List<ExamEntity> examEntities = examRepository.findAll();
		List<ExamEntity> exams = new ArrayList<>();

		if ((keyword != null && !keyword.isEmpty())
				&& (examinationPeriod == false && subject == false && professor == false && examDate == false)) {

			exams.addAll(examEntities.stream()
					.filter(exam -> exam.getExaminationPeriod().getName().toLowerCase().contains(keyword.toLowerCase())
							|| exam.getSubject().getName().toLowerCase().contains(keyword.toLowerCase())
							|| exam.getProfessor().getFirstName().toLowerCase().contains(keyword.toLowerCase())
							|| exam.getProfessor().getLastName().toLowerCase().contains(keyword.toLowerCase())
							|| exam.getExamDate().toString().toLowerCase().contains(keyword.toLowerCase()))
					.collect(Collectors.toList()));
		}

		if (examinationPeriod) {
			exams.addAll(examEntities.stream()
					.filter(exam -> exam.getExaminationPeriod().getName().toLowerCase().contains(keyword.toLowerCase()))
					.collect(Collectors.toList()));
		}

		if (subject) {
			exams.addAll(examEntities.stream()
					.filter(exam -> exam.getSubject().getName().toLowerCase().contains(keyword.toLowerCase()))
					.collect(Collectors.toList()));
		}

		if (professor) {
			exams.addAll(examEntities.stream()
					.filter(exam -> exam.getProfessor().getFirstName().toLowerCase().contains(keyword.toLowerCase())
							|| exam.getProfessor().getLastName().toLowerCase().contains(keyword.toLowerCase()))
					.collect(Collectors.toList()));
		}

		if (examDate) {
			exams.addAll(examEntities.stream()
					.filter(exam -> exam.getExamDate().toString().toLowerCase().contains(keyword.toLowerCase()))
					.collect(Collectors.toList()));
		}

		exams.sort((s1, s2) -> {
	    	 int compareResult = 0;

	         if ("asc".equalsIgnoreCase(sortOrder)) {
	             if ("name".equalsIgnoreCase(sortBy)) {
	                 compareResult = s1.getExamDate().compareTo(s2.getExamDate());
	             }
	         } else if ("desc".equalsIgnoreCase(sortOrder)) {
	             if ("name".equalsIgnoreCase(sortBy)) {
	                 compareResult = s2.getExamDate().compareTo(s1.getExamDate());
	             }
	         }
	        return compareResult != 0 ? compareResult : s1.getId().compareTo(s2.getId());
	    });

	    int totalElements = exams.size();

	    int fromIndex = pageNo * pageSize;
	    int toIndex = Math.min(fromIndex + pageSize, totalElements);

	    List<ExamEntity> pagedExams = exams.subList(fromIndex, toIndex);

	    Pageable pageable = PageRequest.of(pageNo , pageSize, Sort.by(sortOrder, sortBy));
	    return new PageImpl<>(pagedExams, pageable, totalElements);
	}

}
