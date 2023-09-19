package miljana.andric.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import miljana.andric.entities.ExamEntity;
import miljana.andric.exceptions.EngineeringException;
import miljana.andric.services.ExamApplicationService;

@CrossOrigin("*")
@RestController
@RequestMapping("exam_application")
public class ExamApplicationController {

	@Autowired
	private ExamApplicationService examApplicationService;
	
	@PostMapping
    public @ResponseBody ResponseEntity<?> addExam(@RequestBody List<ExamEntity> exams) throws EngineeringException{
		try {
            return ResponseEntity.status(HttpStatus.OK).body(examApplicationService.enrollExams(exams));
        }catch (EngineeringException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
        
    }
	
	@GetMapping
    public @ResponseBody ResponseEntity<?> getAll(){
          return ResponseEntity.status(HttpStatus.OK).body(examApplicationService.getAll());
        
    }
	
	
}
