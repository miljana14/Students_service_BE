package miljana.andric.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import miljana.andric.dtos.TitleDto;
import miljana.andric.entities.TitleEntity;
import miljana.andric.services.TitleService;

@CrossOrigin("*")
@RestController
@RequestMapping("titles")
public class TitleController {
	
	private final TitleService titleService;

    public TitleController(TitleService titleService) {
        this.titleService = titleService;
    }
    
    @GetMapping
    public List<TitleEntity> findAll() {
        return titleService.getAllTitles();
    }
    

    @GetMapping("{id}")
    public @ResponseBody ResponseEntity<Object> findById(@PathVariable Long id) throws Exception {
        Optional<TitleDto> title = titleService.findById(id);
        if (title.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(title.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid!");
    }

   
}
