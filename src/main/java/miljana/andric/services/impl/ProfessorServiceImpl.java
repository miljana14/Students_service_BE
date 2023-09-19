package miljana.andric.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import miljana.andric.dtos.ProfessorRegisterDto;
import miljana.andric.dtos.ProfessorResponseDto;
import miljana.andric.dtos.ProfessorUpdateDto;
import miljana.andric.entities.ProfessorEntity;
import miljana.andric.entities.RoleEnum;
import miljana.andric.entities.SubjectEntity;
import miljana.andric.entities.UserEntity;
import miljana.andric.exceptions.EngineeringException;
import miljana.andric.mappers.ProfessorMapper;
import miljana.andric.repositories.CityRepository;
import miljana.andric.repositories.ProfessorRepository;
import miljana.andric.repositories.SubjectRepository;
import miljana.andric.repositories.UserRepository;
import miljana.andric.services.ProfessorService;

@Service
public class ProfessorServiceImpl implements ProfessorService{
	
	@Autowired
	private ProfessorRepository professorRepository;
	@Autowired
	private ProfessorMapper professorMapper;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private SubjectRepository subjectRepository;
	
	@Override
	public List<ProfessorResponseDto> findAllProfessors() {
		return professorRepository.findAll().stream().map(professorMapper::toDto).collect(Collectors.toList());
	  
	}

	@Override
	public List<ProfessorEntity> findProfessorsByPage(int number) {
		Page<ProfessorEntity> page = professorRepository.findAll(
				PageRequest.of(number + 1, 2, Sort.by(Sort.Direction.ASC, "id")));
		return page.stream().collect(Collectors.toList());
	}

	@Override
	public Optional<ProfessorResponseDto> findById(Long id) throws EngineeringException {
		if(!professorRepository.existsById(id)) {
			throw new EngineeringException(404, "Professor with that id do not found");
		}
		return Optional.of(professorMapper.toDto(professorRepository.findById(id).get()));
     
	}

	@Override
	public ProfessorResponseDto addProfessor(ProfessorRegisterDto professorRegister) throws EngineeringException {
		ProfessorEntity professor = new ProfessorEntity();
		professor.setFirstName(professorRegister.getFirstName());
		professor.setLastName(professorRegister.getLastName());
		if(professorRepository.findByEmail(professorRegister.getEmail()).isPresent()) {
			throw new EngineeringException(1, "Email must be unique");
		}
		professor.setEmail(professorRegister.getEmail());
		professor.setAddress(professorRegister.getAddress());
		if(!cityRepository.existsById(professorRegister.getPostalCode().getPostalCode())) {
			cityRepository.save(professorRegister.getPostalCode());
			professor.setPostalCode(professorRegister.getPostalCode());
			
		}else {
			professor.setPostalCode(professorRegister.getPostalCode());
		}
		professor.setPhone(professorRegister.getPhone());
		professor.setReelectionDate(professorRegister.getReelectionDate());
		professor.setTitle(professorRegister.getTitle());
		professor.setSubjects(professorRegister.getSubjects());
		UserEntity user = new UserEntity();
		user.setFirstName(professorRegister.getFirstName());
		user.setLastName(professorRegister.getLastName());
		if(userRepository.findByEmail(professorRegister.getEmail()).isPresent()) {
			throw new EngineeringException(1, "Email must be unique");
		}
		user.setEmail(professorRegister.getEmail());
		if(userRepository.findById(professorRegister.getUsername()).isPresent()) {
			throw new EngineeringException(1, "Username must be unique");
		}
		user.setUsername(professorRegister.getUsername());
		user.setPassword(professorRegister.getPassword());
		user.setRole(RoleEnum.ROLE_PROFESSOR);
		
		professorRepository.save(professor);
		userRepository.save(user);
		return professorMapper.toDto(professor);
	}

	@Override
	public void deleteProfessor(Long id) throws EngineeringException {
		Optional<ProfessorEntity> professorEntity = professorRepository.findById(id);
        if (professorEntity.isEmpty()) {
            throw new EngineeringException(404,"Not found");
        }
        Optional<UserEntity> userEntity = userRepository.findByEmail(professorEntity.get().getEmail());
		userRepository.delete(userEntity.get());
        professorRepository.delete(professorEntity.get());
		
	}

	@Override
	public ProfessorResponseDto editProfessor(Long id, ProfessorUpdateDto professorRegister) throws EngineeringException {
		
		professorRepository.findById(id).ifPresent(professor -> {
			professor.setFirstName(Optional.ofNullable(professorRegister.getFirstName()).orElse(professor.getFirstName()));
			professor.setLastName(Optional.ofNullable(professorRegister.getLastName()).orElse(professor.getLastName()));
			professor.setAddress(Optional.ofNullable(professorRegister.getAddress()).orElse(professor.getAddress()));
			professor.setPostalCode(Optional.ofNullable(professorRegister.getPostalCode()).orElse(professor.getPostalCode()));	
			professor.setPhone(Optional.ofNullable(professorRegister.getPhone()).orElse(professor.getPhone()));
			professor.setReelectionDate(Optional.ofNullable(professorRegister.getReelectionDate()).orElse(professor.getReelectionDate()));
			professor.setTitle(Optional.ofNullable(professorRegister.getTitle()).orElse(professor.getTitle()));
			
			List<SubjectEntity> existingSubjects = professor.getSubjects();
			List<SubjectEntity> newSubjects = professorRegister.getSubjects();

			if(newSubjects != null) {
				existingSubjects.removeIf(subject -> !newSubjects.contains(subject));
	
				for (SubjectEntity subject : newSubjects) {
					if (!existingSubjects.contains(subject)) {
						existingSubjects.add(subject);
					}
				}
				professor.setSubjects(existingSubjects);
			}
			
			userRepository.findByEmail(professor.getEmail()).ifPresent(user -> {
				user.setFirstName(Optional.ofNullable(professorRegister.getFirstName()).orElse(user.getFirstName())); 
				user.setLastName(Optional.ofNullable(professorRegister.getLastName()).orElse(user.getLastName())); 
				user.setPassword(Optional.ofNullable(professorRegister.getPassword()).orElse(user.getPassword())); 
				user.setRole(Optional.ofNullable(RoleEnum.ROLE_PROFESSOR).orElse(user.getRole())); 
				userRepository.save(user);
			});
			professorRepository.save(professor);
		});
		
		Optional<ProfessorEntity> updatedProfessor = professorRepository.findById(id);
		if(updatedProfessor.isPresent()) {
			return professorMapper.toDto(updatedProfessor.get());
		}
		else {
			throw new EngineeringException(404,"Professor doesn`t exists!");
		}

	}

	@Override
	public Page<ProfessorEntity> findAll(Integer pageNo, Integer pageSize, String sortBy, String sortOrder) {
		Sort.Direction direction = "asc".equalsIgnoreCase(sortOrder) ? Sort.Direction.ASC : Sort.Direction.DESC;
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, sortBy));
	
		return professorRepository.findAll(pageable);
	}

	@Override
	public Page<ProfessorEntity> search(Integer pageNo, Integer pageSize, String sortBy, String sortOrder, 
			 String keyword, boolean firstName, boolean lastName, boolean email, boolean address, boolean postalCode,
	            boolean phone, boolean reelectionDate, boolean title) {

	        List<ProfessorEntity> professorEntities = professorRepository.findAll();
	        List<ProfessorEntity> professors = new ArrayList<>();

	        if ((keyword != null && !keyword.isEmpty()) &&
	            (firstName == false && lastName == false && email == false && address == false && postalCode == false &&
	            phone == false && reelectionDate == false && title == false)) {

	            professors.addAll(professorEntities.stream()
	                .filter(professor ->
	                    professor.getFirstName().toLowerCase().contains(keyword.toLowerCase()) ||
	                    professor.getLastName().toLowerCase().contains(keyword.toLowerCase()) ||
	                    professor.getEmail().toLowerCase().contains(keyword.toLowerCase()) ||
	                    professor.getAddress().toLowerCase().contains(keyword.toLowerCase()) ||
	                    professor.getPostalCode().toString().toLowerCase().contains(keyword.toLowerCase()) ||
	                    professor.getPhone().toLowerCase().contains(keyword.toLowerCase()) ||
	                    professor.getReelectionDate().toString().toLowerCase().contains(keyword.toLowerCase()) ||
	                    professor.getTitle().toString().toLowerCase().contains(keyword.toLowerCase()))
	                .collect(Collectors.toList()));
	        }

	        if (firstName) {
	            professors.addAll(professorEntities.stream()
	                .filter(professor -> professor.getFirstName().toLowerCase().contains(keyword.toLowerCase()))
	                .collect(Collectors.toList()));
	        }

	        if (lastName) {
	            professors.addAll(professorEntities.stream()
	                .filter(professor -> professor.getLastName().toLowerCase().contains(keyword.toLowerCase()))
	                .collect(Collectors.toList()));
	        }

	        if (email) {
	            professors.addAll(professorEntities.stream()
	                .filter(professor -> professor.getEmail().toLowerCase().contains(keyword.toLowerCase()))
	                .collect(Collectors.toList()));
	        }

	        if (address) {
	            professors.addAll(professorEntities.stream()
	                .filter(professor -> professor.getAddress().toLowerCase().contains(keyword.toLowerCase()))
	                .collect(Collectors.toList()));
	        }
	        
	        if (postalCode) {
	            professors.addAll(professorEntities.stream()
	                .filter(professor -> professor.getPostalCode().toString().toLowerCase().contains(keyword.toLowerCase()))
	                .collect(Collectors.toList()));
	        }

	        if (phone) {
	            professors.addAll(professorEntities.stream()
	                .filter(professor -> professor.getPhone().toLowerCase().contains(keyword.toLowerCase()))
	                .collect(Collectors.toList()));
	        }

	        if (reelectionDate) {
	            professors.addAll(professorEntities.stream()
	                .filter(professor -> professor.getReelectionDate().toString().toLowerCase().contains(keyword.toLowerCase()))
	                .collect(Collectors.toList()));
	        }
	        
	        if (title) {
	            professors.addAll(professorEntities.stream()
	                .filter(professor -> professor.getTitle().toString().toLowerCase().contains(keyword.toLowerCase()))
	                .collect(Collectors.toList()));
	        }

	        professors.sort((s1, s2) -> {
				int compareResult = 0;

				if ("asc".equalsIgnoreCase(sortOrder)) {
					if ("name".equalsIgnoreCase(sortBy)) {
						compareResult = s1.getFirstName().compareTo(s2.getFirstName());
					}
				} else if ("desc".equalsIgnoreCase(sortOrder)) {
					if ("name".equalsIgnoreCase(sortBy)) {
						compareResult = s2.getFirstName().compareTo(s1.getFirstName());
					}
				}
				return compareResult != 0 ? compareResult
						: s1.getId().compareTo(s2.getId());
			});

			int totalElements = professors.size();

			int fromIndex = pageNo * pageSize;
			int toIndex = Math.min(fromIndex + pageSize, totalElements);

			List<ProfessorEntity> pagedProfessors = professors.subList(fromIndex, toIndex);

			Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortOrder, sortBy));
			return new PageImpl<>(pagedProfessors, pageable, totalElements);
	    }

	@Override
	public List<ProfessorEntity> availableProfessors(Long subjectId) {
		SubjectEntity subject = subjectRepository.findById(subjectId).get();
		List<ProfessorEntity> professorEntities = subject.getProfessors();
		return professorEntities.stream().collect(Collectors.toList());
	}

	@Override
	public ProfessorEntity addSubjectsToProfessor(Long professorId, List<SubjectEntity> subjects) throws EngineeringException {
		 ProfessorEntity professor = professorRepository.findById(professorId)
		            .orElseThrow(() -> new EngineeringException(404, "Professor not found with id: " + professorId));
		 
		 professor.getSubjects().addAll(subjects);
		 professorRepository.save(professor);
		 return professor;
		
	}

}
