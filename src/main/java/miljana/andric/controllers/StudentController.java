package miljana.andric.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import miljana.andric.dtos.StudentRegisterDto;
import miljana.andric.dtos.StudentResponseDto;
import miljana.andric.dtos.StudentUpdateDto;
import miljana.andric.exceptions.EngineeringException;
import miljana.andric.services.StudentService;
import miljana.andric.services.security.StudentCustomValidator;
import miljana.andric.services.security.StudentUpdateCustomValidator;

@CrossOrigin("*")
@RestController
@RequestMapping("students")
public class StudentController {

	@Autowired
	private StudentService studentService;
	@Autowired
	private StudentCustomValidator studentValidator;
	@Autowired
	private StudentUpdateCustomValidator studentUpdateValidator;
	
	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}

	@InitBinder
	protected void initBinder(final WebDataBinder binder) {
		if (binder.getTarget() instanceof StudentRegisterDto) {
			binder.addValidators(studentValidator);
		}
		if (binder.getTarget() instanceof StudentUpdateDto) {
			binder.addValidators(studentUpdateValidator);
		}
	}

	@GetMapping
	public List<StudentResponseDto> findAll() {
		return studentService.findAllStudents();
	}

	@GetMapping(value = "/{indexNumber}/{indexYear}")
	public ResponseEntity<?> findById(@PathVariable String indexNumber, @PathVariable Long indexYear)
			throws EngineeringException {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(studentService.findById(indexNumber, indexYear));
		} catch (EngineeringException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}

	}

	@PostMapping
	public ResponseEntity<?> addStudent(@Valid @RequestBody StudentRegisterDto student,BindingResult result) throws EngineeringException {
		try {
			if(result.hasErrors()) {
				return new ResponseEntity<>(createErrorMessage(result),HttpStatus.BAD_REQUEST);
			}else {
				studentValidator.validate(student, result);
			}
			return ResponseEntity.status(HttpStatus.OK).body(studentService.addStudent(student));
		} catch (EngineeringException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@DeleteMapping("/{indexNumber}/{indexYear}")
	public ResponseEntity<String> deleteStudent(@PathVariable String indexNumber, @PathVariable Long indexYear)
			throws EngineeringException {
		try {
			studentService.deleteStudent(indexNumber, indexYear);
			return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted " + indexNumber + "/" + indexYear);
		} catch (EngineeringException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PatchMapping("/{indexNumber}/{indexYear}")
	public ResponseEntity<?> editStudent(@PathVariable String indexNumber, @PathVariable Long indexYear, @Valid @RequestBody StudentUpdateDto student, BindingResult result) throws EngineeringException {
		try {
			if(result.hasErrors()) {
				return new ResponseEntity<>(createErrorMessage(result),HttpStatus.BAD_REQUEST);
			}else {
				studentUpdateValidator.validate(student, result);
			}
			return ResponseEntity.status(HttpStatus.OK).body(studentService.editStudent(indexNumber, indexYear, student));
		} catch (EngineeringException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@GetMapping("filter")
	public ResponseEntity<Page<StudentResponseDto>> findAll(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "5") Integer pageSize, @RequestParam(defaultValue = "firstName") String sortBy,
			@RequestParam(defaultValue = "asc") String sortOrder) {
		return new ResponseEntity<Page<StudentResponseDto>>(studentService.findAll(pageNo, pageSize, sortBy, sortOrder),
				new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("search")
	public ResponseEntity<?>  search(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "5") Integer pageSize, @RequestParam(defaultValue = "firstName") String sortBy,
			@RequestParam(defaultValue = "asc") String sortOrder,
			@RequestParam(value = "keyword", required = false) String keyword,
	        @RequestParam(value = "indexNumber", defaultValue = "false") boolean indexNumber,
	        @RequestParam(value = "indexYear", defaultValue = "false") boolean indexYear,
	        @RequestParam(value = "firstName", defaultValue = "false") boolean firstName,
	        @RequestParam(value = "lastName", defaultValue = "false") boolean lastName,
	        @RequestParam(value = "email", defaultValue = "false") boolean email,
	        @RequestParam(value = "address", defaultValue = "false") boolean address,
	        @RequestParam(value = "postalCode", defaultValue = "false") boolean postalCode,
	        @RequestParam(value = "currentYearOfStudy", defaultValue = "false") boolean currentYearOfStudy
	    ) {
	        return ResponseEntity.status(HttpStatus.OK).body(studentService.search(pageNo, pageSize, sortBy, sortOrder, 
		            keyword, indexNumber, indexYear, firstName, lastName, email, address, postalCode, currentYearOfStudy));
	    }
	
	@GetMapping("searchBySubject")
	public ResponseEntity<?>  searchBySubject(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "5") Integer pageSize, @RequestParam(defaultValue = "firstName") String sortBy,
			@RequestParam(defaultValue = "asc") String sortOrder,
			@RequestParam Long subjectId,
			@RequestParam(value = "keyword", required = false) String keyword,
	        @RequestParam(value = "indexNumber", defaultValue = "false") boolean indexNumber,
	        @RequestParam(value = "indexYear", defaultValue = "false") boolean indexYear,
	        @RequestParam(value = "firstName", defaultValue = "false") boolean firstName,
	        @RequestParam(value = "lastName", defaultValue = "false") boolean lastName,
	        @RequestParam(value = "currentYearOfStudy", defaultValue = "false") boolean currentYearOfStudy
	    ) {
	        return ResponseEntity.status(HttpStatus.OK).body(studentService.searchBySubject(subjectId, pageNo, pageSize, sortBy, sortOrder, 
		            keyword, indexNumber, indexYear, firstName, lastName, currentYearOfStudy));
	    }
	
	@GetMapping("studentsForExam/{examId}")
	public ResponseEntity<?> getStudentsForExam(@PathVariable Long examId) {
		return ResponseEntity.status(HttpStatus.OK).body(studentService.getStudentsForExam(examId));
	}
	
	 @GetMapping("subject/{id}")
	    public ResponseEntity<?> getSubjectsStudents(@PathVariable Long id, @RequestParam(defaultValue = "0") Integer pageNo,
				@RequestParam(defaultValue = "5") Integer pageSize, @RequestParam(defaultValue = "firstName") String sortBy,
				@RequestParam(defaultValue = "asc") String sortOrder ) {
			return ResponseEntity.status(HttpStatus.OK).body(studentService.getSubjectsStudentsPage(pageNo, pageSize, sortBy, sortOrder, id));
	    }

}
