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

import miljana.andric.dtos.CityDto;
import miljana.andric.services.CityService;

@CrossOrigin("*")
@RestController
@RequestMapping("cities")
public class CityController {

	private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    public List<CityDto> findAll() {
        return cityService.getAllCities();
    }
    

    @GetMapping("{id}")
    public @ResponseBody ResponseEntity<Object> findById(@PathVariable String id) throws Exception {
        Optional<CityDto> city = cityService.findByPostalCode(id);
        if (city.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(city.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid!");
    }

 }
