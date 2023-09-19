package miljana.andric.services.impl;

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

import miljana.andric.dtos.StudentRegisterDto;
import miljana.andric.dtos.StudentResponseDto;
import miljana.andric.dtos.StudentUpdateDto;
import miljana.andric.entities.ExamApplicationEntity;
import miljana.andric.entities.ExamEntity;
import miljana.andric.entities.Index;
import miljana.andric.entities.RoleEnum;
import miljana.andric.entities.StudentEntity;
import miljana.andric.entities.SubjectEntity;
import miljana.andric.entities.UserEntity;
import miljana.andric.exceptions.EngineeringException;
import miljana.andric.mappers.StudentMapper;
import miljana.andric.repositories.ExamApplicationRepository;
import miljana.andric.repositories.ExamRepository;
import miljana.andric.repositories.StudentRepository;
import miljana.andric.repositories.SubjectRepository;
import miljana.andric.repositories.UserRepository;
import miljana.andric.services.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private StudentMapper studentMapper;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ExamApplicationRepository examApplicationRepository;
	@Autowired
	private ExamRepository examRepository;
	@Autowired
	private SubjectRepository subjectRepository;

	@Override
	public List<StudentResponseDto> findAllStudents() {
		List<StudentEntity> studentEntities = studentRepository.findAll();
		return studentEntities.stream().map(studentMapper::toDto).collect(Collectors.toList());
	}

	@Override
	public List<StudentResponseDto> findStudentsByPage(int number) {
		Page<StudentEntity> page = studentRepository
				.findAll(PageRequest.of(number + 1, 2, Sort.by(Sort.Direction.ASC, "id")));
		return page.stream().map(studentMapper::toDto).collect(Collectors.toList());
	}

	@Override
	public Optional<StudentResponseDto> findById(String indexNumber, Long indexYear) throws EngineeringException {
		Index index = new Index(indexNumber, indexYear);
		if (!studentRepository.existsById(index)) {
			throw new EngineeringException(404, "Student with that index number do not found");
		}
		Optional<StudentEntity> studentEntity = studentRepository.findById(index);
		return studentEntity.map(studentMapper::toDto);
	}

	@Override
	public StudentResponseDto addStudent(StudentRegisterDto studentRegister) throws EngineeringException {
		StudentEntity student = new StudentEntity();
		student.setIndexNumber(studentRegister.getIndexNumber());
		student.setIndexYear(studentRegister.getIndexYear());
		student.setFirstName(studentRegister.getFirstName());
		student.setLastName(studentRegister.getLastName());
		if (studentRepository.findByEmail(studentRegister.getEmail()).isPresent()) {
			throw new EngineeringException(1, "Email must be unique");
		}
		student.setEmail(studentRegister.getEmail());
		student.setAddress(studentRegister.getAddress());
		student.setPostalCode(studentRegister.getPostalCode());
		student.setPostalCode(studentRegister.getPostalCode());
		student.setCurrentYearOfStudy(studentRegister.getCurrentYearOfStudy());

		UserEntity user = new UserEntity();
		user.setFirstName(studentRegister.getFirstName());
		user.setLastName(studentRegister.getLastName());
		if (userRepository.findByEmail(studentRegister.getEmail()).isPresent()) {
			throw new EngineeringException(1, "Email must be unique");
		}
		user.setEmail(studentRegister.getEmail());
		if (userRepository.findById(studentRegister.getUsername()).isPresent()) {
			throw new EngineeringException(1, "Username must be unique");
		}
		user.setUsername(studentRegister.getUsername());
		user.setPassword(studentRegister.getPassword());
		user.setRole(RoleEnum.ROLE_STUDENT);
		user.setNewUser(true);

		List<SubjectEntity> subjects = subjectRepository.findAll().stream()
				.filter(subject -> student.getCurrentYearOfStudy() >= subject.getYearOfStudy())
				.collect(Collectors.toList());

		student.setSubjects(subjects);

		studentRepository.save(student);
		userRepository.save(user);
		return studentMapper.toDto(student);
	}

	@Override
	public void deleteStudent(String indexNumber, Long indexYear) throws EngineeringException {
		Index index = new Index(indexNumber, indexYear);
		Optional<StudentEntity> studentEntity = studentRepository.findById(index);
		if (studentEntity.isEmpty()) {
			throw new EngineeringException(400, "Doesn`t exist");
		}
		Optional<UserEntity> userEntity = userRepository.findByEmail(studentEntity.get().getEmail());
		studentRepository.delete(studentEntity.get());
		userRepository.delete(userEntity.get());
	}

	@Override
	public StudentResponseDto editStudent(String indexNumber, Long indexYear, StudentUpdateDto studentRegister)
			throws EngineeringException {
		Index index = new Index(indexNumber, indexYear);

		studentRepository.findById(index).ifPresent(student -> {
			student.setFirstName(Optional.ofNullable(studentRegister.getFirstName()).orElse(student.getFirstName()));
			student.setLastName(Optional.ofNullable(studentRegister.getLastName()).orElse(student.getLastName()));
			student.setAddress(Optional.ofNullable(studentRegister.getAddress()).orElse(student.getAddress()));
			student.setPostalCode(Optional.ofNullable(studentRegister.getPostalCode()).orElse(student.getPostalCode()));
			student.setCurrentYearOfStudy(Optional.ofNullable(studentRegister.getCurrentYearOfStudy())
					.orElse(student.getCurrentYearOfStudy()));

			userRepository.findByEmail(Optional.ofNullable(studentRegister.getEmail()).orElse(student.getEmail()))
					.ifPresent(user -> {
						user.setFirstName(
								Optional.ofNullable(studentRegister.getFirstName()).orElse(user.getFirstName()));
						user.setLastName(Optional.ofNullable(studentRegister.getLastName()).orElse(user.getLastName()));
						user.setPassword(Optional.ofNullable(studentRegister.getPassword()).orElse(user.getPassword()));
						user.setRole(Optional.ofNullable(RoleEnum.ROLE_STUDENT).orElse(user.getRole()));
						userRepository.save(user);
					});

			List<SubjectEntity> subjects = subjectRepository.findAll().stream()
					.filter(subject -> student.getCurrentYearOfStudy() >= subject.getYearOfStudy())
					.collect(Collectors.toList());

			student.setSubjects(subjects);
			studentRepository.save(student);
		});

		Optional<StudentEntity> updatedStudent = studentRepository.findById(index);

		if (updatedStudent.isPresent()) {
			return studentMapper.toDto(updatedStudent.get());
		} else {
			throw new EngineeringException(404, "Student not found with index: " + indexNumber + "/" + indexYear);
		}

	}

	@Override
	public Page<StudentResponseDto> findAll(Integer pageNo, Integer pageSize, String sortBy, String sortOrder) {
		Sort.Direction direction = "asc".equalsIgnoreCase(sortOrder) ? Sort.Direction.ASC : Sort.Direction.DESC;

		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, sortBy));
		Page<StudentResponseDto> entites = studentRepository.findAll(pageable).map(studentMapper::toDto);
		return entites;
	}

	@Override
	public Page<StudentEntity> search(Integer pageNo, Integer pageSize, String sortBy, String sortOrder, String keyword,
			boolean indexNumber, boolean indexYear, boolean firstName, boolean lastName, boolean email, boolean address,
			boolean postalCode, boolean currentYearOfStudy) {
		List<StudentEntity> studentEntities = studentRepository.findAll();
		List<StudentEntity> students = new ArrayList<>();

		if ((keyword != null && !keyword.isEmpty()) && !(indexNumber || indexYear || firstName || lastName || email
				|| address || postalCode || currentYearOfStudy)) {
			students.addAll(studentEntities.stream()
					.filter(student -> student.getIndexNumber().toLowerCase().contains(keyword.toLowerCase())
							|| student.getIndexYear().toString().toLowerCase().contains(keyword.toLowerCase())
							|| student.getFirstName().toLowerCase().contains(keyword.toLowerCase())
							|| student.getLastName().toLowerCase().contains(keyword.toLowerCase())
							|| student.getEmail().toLowerCase().contains(keyword.toLowerCase())
							|| student.getAddress().toLowerCase().contains(keyword.toLowerCase())
							|| student.getPostalCode().toString().toLowerCase().contains(keyword.toLowerCase())
							|| student.getCurrentYearOfStudy().toString().contains(keyword))
					.collect(Collectors.toList()));
		}

		if (indexNumber) {
			students.addAll(studentEntities.stream()
					.filter(student -> student.getIndexNumber().toLowerCase().contains(keyword.toLowerCase()))
					.collect(Collectors.toList()));
		}

		if (indexYear) {
			students.addAll(
					studentEntities.stream().filter(student -> student.getIndexYear().toString().contains(keyword))
							.collect(Collectors.toList()));
		}

		if (firstName) {
			students.addAll(studentEntities.stream()
					.filter(student -> student.getFirstName().toLowerCase().contains(keyword.toLowerCase()))
					.collect(Collectors.toList()));
		}

		if (lastName) {
			students.addAll(studentEntities.stream()
					.filter(student -> student.getLastName().toLowerCase().contains(keyword.toLowerCase()))
					.collect(Collectors.toList()));
		}

		if (email) {
			students.addAll(studentEntities.stream()
					.filter(student -> student.getEmail().toLowerCase().contains(keyword.toLowerCase()))
					.collect(Collectors.toList()));
		}

		if (address) {
			students.addAll(studentEntities.stream()
					.filter(student -> student.getAddress().toLowerCase().contains(keyword.toLowerCase()))
					.collect(Collectors.toList()));
		}

		if (postalCode) {
			students.addAll(studentEntities.stream()
					.filter(student -> student.getPostalCode().toString().toLowerCase().contains(keyword.toLowerCase()))
					.collect(Collectors.toList()));
		}

		if (currentYearOfStudy) {
			students.addAll(studentEntities.stream()
					.filter(student -> student.getCurrentYearOfStudy().toString().contains(keyword))
					.collect(Collectors.toList()));
		}

		students.sort((s1, s2) -> {
			int compareResult = 0;

			if ("asc".equalsIgnoreCase(sortOrder)) {
				if ("name".equalsIgnoreCase(sortBy)) {
					compareResult = s1.getFirstName().compareTo(s2.getFirstName());
				}
			} else if ("desc".equalsIgnoreCase(sortOrder)) {
				if ("name".equalsIgnoreCase(sortBy)) {
					compareResult = s2.getFirstName().compareTo(s1.getFirstName());
				}
			}
			return compareResult != 0 ? compareResult
					: s1.getIndexNumber().compareTo(s2.getIndexNumber()) != 0
							? s1.getIndexNumber().compareTo(s2.getIndexNumber())
							: s1.getIndexYear().compareTo(s2.getIndexYear());
		});

		int totalElements = students.size();

		int fromIndex = pageNo * pageSize;
		int toIndex = Math.min(fromIndex + pageSize, totalElements);

		List<StudentEntity> pagedStudents = students.subList(fromIndex, toIndex);

		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortOrder, sortBy));
		return new PageImpl<>(pagedStudents, pageable, totalElements);
	}
	
	@Override
	public Page<StudentEntity> searchBySubject(Long subjectId, Integer pageNo, Integer pageSize, String sortBy, String sortOrder, String keyword,
			boolean indexNumber, boolean indexYear, boolean firstName, boolean lastName, boolean currentYearOfStudy) {
		
		SubjectEntity subject = subjectRepository.findById(subjectId).get();
		List<StudentEntity> studentEntities = subject.getStudents();

		List<StudentEntity> students = new ArrayList<>();

		if (keyword == null || keyword.isEmpty()) {
		    students.addAll(studentEntities);
		} else if (!(indexNumber || indexYear || firstName || lastName || currentYearOfStudy)) {
		    students.addAll(studentEntities.stream()
		            .filter(student -> student.getIndexNumber().toLowerCase().contains(keyword.toLowerCase())
		                    || student.getIndexYear().toString().toLowerCase().contains(keyword.toLowerCase())
		                    || student.getFirstName().toLowerCase().contains(keyword.toLowerCase())
		                    || student.getLastName().toLowerCase().contains(keyword.toLowerCase())
		                    || student.getCurrentYearOfStudy().toString().contains(keyword))
		            .collect(Collectors.toList()));
		}
		if (indexNumber) {
			students.addAll(studentEntities.stream()
					.filter(student -> student.getIndexNumber().toLowerCase().contains(keyword.toLowerCase()))
					.collect(Collectors.toList()));
		}

		if (indexYear) {
			students.addAll(
					studentEntities.stream().filter(student -> student.getIndexYear().toString().contains(keyword))
							.collect(Collectors.toList()));
		}

		if (firstName) {
			students.addAll(studentEntities.stream()
					.filter(student -> student.getFirstName().toLowerCase().contains(keyword.toLowerCase()))
					.collect(Collectors.toList()));
		}

		if (lastName) {
			students.addAll(studentEntities.stream()
					.filter(student -> student.getLastName().toLowerCase().contains(keyword.toLowerCase()))
					.collect(Collectors.toList()));
		}

		
		if (currentYearOfStudy) {
			students.addAll(studentEntities.stream()
					.filter(student -> student.getCurrentYearOfStudy().toString().contains(keyword))
					.collect(Collectors.toList()));
		}

		students.sort((s1, s2) -> {
			int compareResult = 0;

			if ("asc".equalsIgnoreCase(sortOrder)) {
				if ("name".equalsIgnoreCase(sortBy)) {
					compareResult = s1.getFirstName().compareTo(s2.getFirstName());
				}
			} else if ("desc".equalsIgnoreCase(sortOrder)) {
				if ("name".equalsIgnoreCase(sortBy)) {
					compareResult = s2.getFirstName().compareTo(s1.getFirstName());
				}
			}
			return compareResult != 0 ? compareResult
					: s1.getIndexNumber().compareTo(s2.getIndexNumber()) != 0
							? s1.getIndexNumber().compareTo(s2.getIndexNumber())
							: s1.getIndexYear().compareTo(s2.getIndexYear());
		});

		int totalElements = students.size();

		int fromIndex = pageNo * pageSize;
		int toIndex = Math.min(fromIndex + pageSize, totalElements);

		List<StudentEntity> pagedStudents = students.subList(fromIndex, toIndex);

		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortOrder, sortBy));
		return new PageImpl<>(pagedStudents, pageable, totalElements);
	}


	@Override
	public List<StudentEntity> getStudentsForExam(Long examId) {
		ExamEntity exam = examRepository.findById(examId).get();
		List<StudentEntity> students = examApplicationRepository.findAll().stream()
				.filter(application -> application.getExams().contains(exam)) //&& !markRepository.existsByExamAndStudent(exam, application.getStudent())
				.map(ExamApplicationEntity::getStudent)
				.collect(Collectors.toList());

		return students;
	}

	
	public List<StudentEntity> getSubjectsStudents(Long id) {
		SubjectEntity subject = subjectRepository.findById(id).get();
		return subject.getStudents();

	}
	
	@Override
	public Page<StudentEntity> getSubjectsStudentsPage(Integer pageNo, Integer pageSize, String sortBy, String sortOrder, Long id) {
		SubjectEntity subject = subjectRepository.findById(id).get();
		List<StudentEntity> students = subject.getStudents();
		int totalElements = students.size();

		int fromIndex = pageNo * pageSize;
		int toIndex = Math.min(fromIndex + pageSize, totalElements);

		List<StudentEntity> pagedStudents = students.subList(fromIndex, toIndex);

		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortOrder, sortBy));
		return new PageImpl<>(pagedStudents, pageable, totalElements);

	}

}
