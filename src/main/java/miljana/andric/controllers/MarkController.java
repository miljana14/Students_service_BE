package miljana.andric.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import miljana.andric.exceptions.EngineeringException;
import miljana.andric.services.MarkService;

@CrossOrigin("*")
@RestController
@RequestMapping("marks")
public class MarkController {
	
	@Autowired
	private MarkService markService;
	
	
	//string index se unosi u formatu nnnn/yyyy
	@PostMapping
    public @ResponseBody ResponseEntity<?> addExam(@RequestParam Long examId, @RequestParam String index, @RequestParam Integer mark) throws EngineeringException{
		try {
            return ResponseEntity.status(HttpStatus.OK).body(markService.addMark(examId, index, mark));
        }catch (EngineeringException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
        
    }
	
	@GetMapping("/students")
	public @ResponseBody ResponseEntity<?> getExams() throws EngineeringException{
		try {
			return ResponseEntity.status(HttpStatus.OK).body(markService.marksForStudent());
		 }catch (EngineeringException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@GetMapping
	public @ResponseBody ResponseEntity<?> getAll() throws EngineeringException{
		return ResponseEntity.status(HttpStatus.OK).body(markService.getAll());
	}

}
