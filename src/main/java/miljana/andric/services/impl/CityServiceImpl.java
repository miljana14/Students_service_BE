package miljana.andric.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import miljana.andric.dtos.CityDto;
import miljana.andric.entities.CityEntity;
import miljana.andric.exceptions.EngineeringException;
import miljana.andric.mappers.CityMapper;
import miljana.andric.repositories.CityRepository;
import miljana.andric.services.CityService;

@Service
public class CityServiceImpl implements CityService{
	
	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private CityMapper cityMapper;

	@Override
	public List<CityDto> getAllCities() {
		return cityRepository.findAll().stream().map(cityMapper::toDto).collect(Collectors.toList());
	}

	@Override
	public Optional<CityDto> findByPostalCode(String postalCode) throws EngineeringException {
		Optional<CityEntity> city = cityRepository.findById(postalCode);
		if (!city.isPresent()) {
			throw new EngineeringException(404, "Not found");
		}
		return Optional.of(cityMapper.toDto(city.get()));
	}

	@Override
	public Optional<CityDto> findByName(String name) throws EngineeringException {
		Optional<CityEntity> city = cityRepository.findByName(name);
		if (!city.isPresent()) {
			throw new EngineeringException(404, "Not found");
		}
		return Optional.of(cityMapper.toDto(city.get()));
	}

}
