package miljana.andric.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
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

import miljana.andric.dtos.ExaminationPeriodDto;
import miljana.andric.dtos.ExaminationPeriodUpdateDto;
import miljana.andric.entities.ExaminationPeriod;
import miljana.andric.exceptions.EngineeringException;
import miljana.andric.services.ExaminationPeriodService;

@CrossOrigin("*")
@RestController
@RequestMapping("examination_period")
public class ExaminationPeriodController {

	@Autowired
	private ExaminationPeriodService examinationPeriodService;
	
	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}
	
	 @GetMapping
	    public List<ExaminationPeriod> findAll(){
	    	return examinationPeriodService.getAllExaminationPeriods();
	    }
	 
	 @GetMapping("presentOrFuture")
	    public List<ExaminationPeriod> findAllPresentOrFuture(){
	    	return examinationPeriodService.getAllExaminationPeriodsPresentOrFuture();
	    }
	 
	 
	 @GetMapping(value = "{id}")
	    public @ResponseBody ResponseEntity<?> findByid(@PathVariable Long id) throws EngineeringException{
	    	try {
	            return ResponseEntity.status(HttpStatus.OK).body(examinationPeriodService.findById(id));
	        }catch (EngineeringException e) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
			}
	        
	    }


	    @GetMapping(value = "name/{name}")
	    public @ResponseBody ResponseEntity<?> findByExName(@PathVariable String name) throws Exception{
	    	try {
	            return ResponseEntity.status(HttpStatus.OK).body(examinationPeriodService.findByName(name));
	        }catch (EngineeringException e) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
			}
	        
	    }
	    
	    @PostMapping
	    public @ResponseBody ResponseEntity<?> addExaminationPeriod(@Valid @RequestBody ExaminationPeriodDto examinationPeriod,BindingResult result) throws EngineeringException{
	    	try {
	    		if(result.hasErrors()) {
					return new ResponseEntity<>(createErrorMessage(result),HttpStatus.BAD_REQUEST);
				}
	            return ResponseEntity.status(HttpStatus.OK).body(examinationPeriodService.addExaminationPeriod(examinationPeriod));
	        }catch (EngineeringException e) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
			}
	    }
	    
	    @PatchMapping("{id}")
	    public @ResponseBody ResponseEntity<?> editExaminationPeriod(@PathVariable Long id, @Valid @RequestBody ExaminationPeriodUpdateDto examinationPeriod,BindingResult result){
	    	try {
	    		if(result.hasErrors()) {
					return new ResponseEntity<>(createErrorMessage(result),HttpStatus.BAD_REQUEST);
				}
	            return ResponseEntity.status(HttpStatus.OK).body(examinationPeriodService.editExaminationPeriod(id, examinationPeriod));
	        }catch (EngineeringException e) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
			}
	    }
	    
	    @GetMapping("filter")
		public ResponseEntity<Page<ExaminationPeriod>> findAll(@RequestParam(defaultValue = "0") Integer pageNo,
				@RequestParam(defaultValue = "5") Integer pageSize, @RequestParam(defaultValue = "name") String sortBy,
				@RequestParam(defaultValue = "asc") String sortOrder) {
	    	return new ResponseEntity<Page<ExaminationPeriod>>(examinationPeriodService.findAll(pageNo, pageSize, sortBy, sortOrder), new HttpHeaders(), HttpStatus.OK);
	    }
	    
	    @DeleteMapping("{id}")
	    public @ResponseBody ResponseEntity<?> deleteExaminationPeriod(@PathVariable Long id) throws EngineeringException{
	    	try {
	    		examinationPeriodService.delete(id);
	    		return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted");
	    	}catch (EngineeringException e) {
	    		 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
			}
	    }
	    
//	    @Scheduled(cron = "0 0 0 * * ?", zone = "Europe/Belgrade")
//	    public  ResponseEntity<?> deleteExaminationPeriodsFromPast() {
//	    	examinationPeriodService.deleteExaminationPeriodsFromPast();
//	    	return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted");
//	    }
//	    
	    
	    @PostMapping("examinationPeriodByClone")
	    public @ResponseBody ResponseEntity<?> addExaminationPeriodByClone(@RequestParam String name, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate beginDate, @RequestParam String cloneName) throws EngineeringException{
	    	try {
	    		return ResponseEntity.status(HttpStatus.OK).body(examinationPeriodService.addExaminationPeriodByClone(name, beginDate, cloneName));
	    	}catch (EngineeringException e) {
	    		 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
			}
	    }
	    
	    @GetMapping("/search")
	    public ResponseEntity<?> searchExaminationPeriods(@RequestParam(defaultValue = "0") Integer pageNo,
				@RequestParam(defaultValue = "5") Integer pageSize, @RequestParam(defaultValue = "name") String sortBy,
				@RequestParam(defaultValue = "asc") String sortOrder,
	            @RequestParam(value = "keyword", required = false) String keyword,
	            @RequestParam(value = "name", defaultValue = "false") boolean name,
	            @RequestParam(value = "beginDate", defaultValue = "false") boolean beginDate,
	            @RequestParam(value = "endDate", defaultValue = "false") boolean endDate,
	            @RequestParam(value = "active", defaultValue = "false") boolean active) {
	        
	        return ResponseEntity.status(HttpStatus.OK).body(examinationPeriodService.search(pageNo, pageSize, sortBy, sortOrder, keyword, name, beginDate, endDate, active));
	    }
}
