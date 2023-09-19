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

import miljana.andric.entities.ExamEntity;
import miljana.andric.exceptions.EngineeringException;
import miljana.andric.services.ExamService;

@CrossOrigin("*")
@RestController
@RequestMapping("exams")
public class ExamController {
	
	@Autowired
	private ExamService examService;
	
	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}
	
	@GetMapping
    public List<ExamEntity> findAll(){
    	return examService.getAllExams();
    }
	
	 @GetMapping(value = "{id}")
	    public @ResponseBody ResponseEntity<?> findById(@PathVariable Long id) throws EngineeringException{
			try {
	            return ResponseEntity.status(HttpStatus.OK).body(examService.findById(id));
	        }catch (EngineeringException e) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
			}
	 }

	 @PostMapping
	    public @ResponseBody ResponseEntity<?> addExam(@Valid @RequestBody ExamEntity exam, BindingResult result) throws EngineeringException{
			try {
				if(result.hasErrors()) {
					return new ResponseEntity<>(createErrorMessage(result),HttpStatus.BAD_REQUEST);
				}
	            return ResponseEntity.status(HttpStatus.OK).body(examService.addExam(exam));
	        }catch (EngineeringException e) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
			}
	        
	    }
	    
	    @PatchMapping("{id}")
	    public @ResponseBody ResponseEntity<?> editExam(@PathVariable Long id, @Valid @RequestBody ExamEntity exam, BindingResult result) throws EngineeringException{
	    	try {
	    		if(result.hasErrors()) {
					return new ResponseEntity<>(createErrorMessage(result),HttpStatus.BAD_REQUEST);
				}
	            return ResponseEntity.status(HttpStatus.OK).body(examService.editExam(id, exam));
	        }catch (EngineeringException e) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
			}
	        
	    }
	    
	    @DeleteMapping("{id}")
	    public @ResponseBody ResponseEntity<?> deleteExam(@PathVariable Long id) throws EngineeringException{
	    	try {
	    		examService.deleteExam(id);
	    		return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted");
	    	}catch (EngineeringException e) {
	    		 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
			}
	    }
	    
	    @GetMapping("filter")
		public ResponseEntity<Page<ExamEntity>> findAll(@RequestParam(defaultValue = "0") Integer pageNo,
				@RequestParam(defaultValue = "5") Integer pageSize, @RequestParam(defaultValue = "examDate") String sortBy,
				@RequestParam(defaultValue = "asc") String sortOrder) {
	    	return new ResponseEntity<Page<ExamEntity>>(examService.findAll(pageNo, pageSize, sortBy, sortOrder), new HttpHeaders(), HttpStatus.OK);
	    }
	    
	    @GetMapping("filterByExaminationPeriod/{examinationPeriod}")
		public ResponseEntity<?> findByExaminationPeriod(@PathVariable Long examinationPeriod) {
	    	return new ResponseEntity<>(examService.findByExaminationPeriod(examinationPeriod), new HttpHeaders(), HttpStatus.OK);
	    }
	    
	    @GetMapping(value = "availableDates/{examinationPeriodId}")
	    public @ResponseBody ResponseEntity<?> availableDates(@PathVariable Long examinationPeriodId) throws EngineeringException{
	         return ResponseEntity.status(HttpStatus.OK).body(examService.availableDates(examinationPeriodId));
	    }
	    
	    @GetMapping(value = "forStudent")
	    public @ResponseBody ResponseEntity<?> getAvailableExamsForStudent() throws EngineeringException{
			try {
	            return ResponseEntity.status(HttpStatus.OK).body(examService.getAvailableExamsForStudent());
	        }catch (EngineeringException e) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
			}
	    }
	    
	    @GetMapping(value = "forProfessor")
	    public @ResponseBody ResponseEntity<?> getAvailableExamsForProfessor() throws EngineeringException{
			try {
	            return ResponseEntity.status(HttpStatus.OK).body(examService.getAvailableExamsForProfessor());
	        }catch (EngineeringException e) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
			}
	    }
	    
	    @GetMapping("/search")
	    public ResponseEntity<?> searchExams(@RequestParam(defaultValue = "0") Integer pageNo,
				@RequestParam(defaultValue = "5") Integer pageSize, @RequestParam(defaultValue = "firstName") String sortBy,
				@RequestParam(defaultValue = "asc") String sortOrder,
	            @RequestParam(value = "keyword", required = false) String keyword,
	            @RequestParam(value = "examinationPeriod", defaultValue = "false") boolean examinationPeriod,
	            @RequestParam(value = "subject", defaultValue = "false") boolean subject,
	            @RequestParam(value = "professor", defaultValue = "false") boolean professor,
	            @RequestParam(value = "examDate", defaultValue = "false") boolean examDate) {
	        
	        return ResponseEntity.status(HttpStatus.OK).body(examService.search(pageNo, pageSize, sortBy, sortOrder, keyword, examinationPeriod, subject, professor, examDate));
	    }
}
