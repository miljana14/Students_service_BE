package miljana.andric.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import miljana.andric.dtos.SubjectDto;
import miljana.andric.dtos.SubjectUpdateDto;
import miljana.andric.entities.SubjectEntity;
import miljana.andric.exceptions.EngineeringException;
import miljana.andric.services.SubjectService;

@CrossOrigin("*")
@RestController
@RequestMapping("subjects")
public class SubjectController {

	@Autowired
	private SubjectService subjectService;

	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}

	@GetMapping
	public List<SubjectEntity> findAll() {
		return subjectService.findAllSubjects();
	}

	@GetMapping(value = "{id}")
	public @ResponseBody ResponseEntity<Object> findById(@PathVariable Long id) throws EngineeringException {
		Optional<SubjectEntity> subjectDto = subjectService.findById(id);
		return subjectDto.<ResponseEntity<Object>>map(dto -> ResponseEntity.status(HttpStatus.OK).body(dto))
				.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid ID!"));
	}

	@PostMapping
	public @ResponseBody ResponseEntity<Object> addSubject(@Valid @RequestBody SubjectDto subject,
			BindingResult result) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.status(HttpStatus.OK).body(subjectService.addSubject(subject));

	}

	@DeleteMapping("{id}")
	public @ResponseBody ResponseEntity<String> deleteSubject(@PathVariable Long id) {
		try {
			subjectService.deleteSubject(id);
			return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted " + id);
		} catch (EngineeringException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PatchMapping("{id}")
	public @ResponseBody ResponseEntity<Object> editSubject(@PathVariable Long id,
			@Valid @RequestBody SubjectUpdateDto subject) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(subjectService.editSubject(id, subject));
		} catch (EngineeringException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@GetMapping("filter")
	public ResponseEntity<Page<SubjectEntity>> findAll(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "5") Integer pageSize, @RequestParam(defaultValue = "name") String sortBy,
			@RequestParam(defaultValue = "asc") String sortOrder) {
		return new ResponseEntity<Page<SubjectEntity>>(subjectService.findAll(pageNo, pageSize, sortBy, sortOrder),
				new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("search")
	public ResponseEntity<?> search(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "5") Integer pageSize, @RequestParam(defaultValue = "name") String sortBy,
			@RequestParam(defaultValue = "asc") String sortOrder,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "name", required = false) boolean name,
			@RequestParam(value = "description", required = false) boolean description,
			@RequestParam(value = "semester", required = false) boolean semester,
			@RequestParam(value = "noOfESP", required = false) boolean noOfESP,
			@RequestParam(value = "yearOfStudy", required = false) boolean yearOfStudy) {
		return new ResponseEntity<>(subjectService.search(pageNo, pageSize, sortBy, sortOrder, keyword, name, description, semester, noOfESP, yearOfStudy),
				new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("professor/{professorId}")
	public ResponseEntity<?> findByProfessor(@PathVariable Long professorId) {
		return ResponseEntity.status(HttpStatus.OK).body(subjectService.availableSubjects(professorId));
	}

	@GetMapping("professorDontHave/{professorId}")
	public ResponseEntity<?> findByProfessorDontHave(@PathVariable Long professorId) {
		return ResponseEntity.status(HttpStatus.OK).body(subjectService.subjectsProfessorDontHave(professorId));
	}

	@DeleteMapping("{professorId}/subjects/{subjectId}")
	public ResponseEntity<?> removeSubjectFromProfessor(@PathVariable Long professorId, @PathVariable Long subjectId) {
		subjectService.removeSubjectFromProfessor(professorId, subjectId);
		return ResponseEntity.ok().build();
	}

	@GetMapping("student/{indexNumber}/{indexYear}")
	public ResponseEntity<?> getStudentsSubjects(@PathVariable String indexNumber, @PathVariable Long indexYear) {
		return ResponseEntity.status(HttpStatus.OK).body(subjectService.getStudentsSubjects(indexNumber, indexYear));
	}

}
