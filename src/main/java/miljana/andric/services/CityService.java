package miljana.andric.services;

import java.util.List;
import java.util.Optional;

import miljana.andric.dtos.CityDto;
import miljana.andric.exceptions.EngineeringException;

public interface CityService {
	
	List<CityDto> getAllCities();
	
	Optional<CityDto> findByPostalCode(String postalCode) throws EngineeringException;
	
	Optional<CityDto> findByName(String name) throws EngineeringException;

}
