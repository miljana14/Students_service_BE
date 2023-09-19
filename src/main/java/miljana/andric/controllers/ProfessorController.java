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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import miljana.andric.dtos.ProfessorRegisterDto;
import miljana.andric.dtos.ProfessorResponseDto;
import miljana.andric.dtos.ProfessorUpdateDto;
import miljana.andric.entities.ProfessorEntity;
import miljana.andric.entities.SubjectEntity;
import miljana.andric.exceptions.EngineeringException;
import miljana.andric.services.ProfessorService;
import miljana.andric.services.security.ProfessorCustomValidator;
import miljana.andric.services.security.ProfessorUpdateCustomValidator;

@CrossOrigin("*")
@RestController
@RequestMapping("professors")
public class ProfessorController {
	
	@Autowired
	private ProfessorService professorService;
	@Autowired
	private ProfessorCustomValidator professorValidator;
	@Autowired
	private ProfessorUpdateCustomValidator professorUpdateValidator;
	
	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}

	@InitBinder
	protected void initBinder(final WebDataBinder binder) {
		if (binder.getTarget() instanceof ProfessorRegisterDto) {
			binder.addValidators(professorValidator);
		}
		if (binder.getTarget() instanceof ProfessorUpdateDto) {
			binder.addValidators(professorUpdateValidator);
		}
	}
	
	@GetMapping
	public List<ProfessorResponseDto> findAll(){
		return professorService.findAllProfessors();
	}
	
	 @GetMapping(value = "{id}")
	    public @ResponseBody ResponseEntity<Object> findById(@PathVariable Long id) throws EngineeringException{
		 try {
				return ResponseEntity.status(HttpStatus.OK).body(professorService.findById(id));
			} catch (EngineeringException e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
			}
		 }

	    @PostMapping
	    public @ResponseBody ResponseEntity<?> addProfessor(@Valid @RequestBody ProfessorRegisterDto professor,BindingResult result) throws EngineeringException{
	    	try {
				if(result.hasErrors()) {
					return new ResponseEntity<>(createErrorMessage(result),HttpStatus.BAD_REQUEST);
				}else {
					professorValidator.validate(professor, result);
				}
				return ResponseEntity.status(HttpStatus.OK).body(professorService.addProfessor(professor));
			} catch (EngineeringException e) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
			}
		}
	    

	    @DeleteMapping("{id}")
	    public @ResponseBody ResponseEntity<String> deleteProfessor( @PathVariable Long id) throws EngineeringException{
	    	try {
	    		professorService.deleteProfessor(id);
	    		return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted " + id);
	    	}catch (EngineeringException e) {
	    		 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
			}
	    }

	    @PatchMapping("{id}")
	    public @ResponseBody ResponseEntity<?> editProfessor(@PathVariable Long id, @Valid @RequestBody ProfessorUpdateDto professor,BindingResult result) throws EngineeringException{
	    	try {
				if(result.hasErrors()) {
					return new ResponseEntity<>(createErrorMessage(result),HttpStatus.BAD_REQUEST);
				}else {
					professorUpdateValidator.validate(professor, result);
				}
				return ResponseEntity.status(HttpStatus.OK).body(professorService.editProfessor(id, professor));
			} catch (EngineeringException e) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
			}
		}
	    
	    @GetMapping("filter")
		public ResponseEntity<Page<ProfessorEntity>> findAll(@RequestParam(defaultValue = "0") Integer pageNo,
				@RequestParam(defaultValue = "5") Integer pageSize, @RequestParam(defaultValue = "firstName") String sortBy,
				@RequestParam(defaultValue = "asc") String sortOrder) {
	    	return new ResponseEntity<Page<ProfessorEntity>>(professorService.findAll(pageNo, pageSize, sortBy, sortOrder), new HttpHeaders(), HttpStatus.OK);
	    }
	    
	    @GetMapping("search")
	    public ResponseEntity<?> search(@RequestParam(defaultValue = "0") Integer pageNo,
				@RequestParam(defaultValue = "5") Integer pageSize, @RequestParam(defaultValue = "firstName") String sortBy,
				@RequestParam(defaultValue = "asc") String sortOrder,
				@RequestParam(value = "keyword", required = false) String keyword,
	            @RequestParam(value = "firstName", defaultValue = "false") boolean firstName,
	            @RequestParam(value = "lastName", defaultValue = "false") boolean lastName,
	            @RequestParam(value = "email", defaultValue = "false") boolean email,
	            @RequestParam(value = "address", defaultValue = "false") boolean address,
	            @RequestParam(value = "postalCode", defaultValue = "false") boolean postalCode,
	            @RequestParam(value = "phone", defaultValue = "false") boolean phone,
	            @RequestParam(value = "reelectionDate", defaultValue = "false") boolean reelectionDate,
	            @RequestParam(value = "title", defaultValue = "false") boolean title
	    ) {
	    	return ResponseEntity.status(HttpStatus.OK).body(professorService.search(pageNo, pageSize, sortBy, sortOrder, 
	            keyword, firstName, lastName, email, address, postalCode, phone, reelectionDate, title));
	    }
	    
	    @PostMapping("subjects/{professorId}")
	    public ResponseEntity<?> addSubjectToProfessor(@PathVariable Long professorId, @RequestBody List<SubjectEntity> subjects) {
	    	try {
				return ResponseEntity.status(HttpStatus.OK).body(professorService.addSubjectsToProfessor(professorId, subjects));
			} catch (EngineeringException e) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
			}
	    }
	    
	    @GetMapping("availableProfessors/{subjectId}")
		public ResponseEntity<?> findAll(@PathVariable Long subjectId){
	    	return ResponseEntity.status(HttpStatus.OK).body(professorService.availableProfessors(subjectId));
		}
	    
	  
	}
