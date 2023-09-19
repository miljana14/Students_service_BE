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

import miljana.andric.dtos.SubjectDto;
import miljana.andric.dtos.SubjectUpdateDto;
import miljana.andric.entities.ProfessorEntity;
import miljana.andric.entities.StudentEntity;
import miljana.andric.entities.SubjectEntity;
import miljana.andric.exceptions.EngineeringException;
import miljana.andric.mappers.SubjectMapper;
import miljana.andric.repositories.ProfessorRepository;
import miljana.andric.repositories.StudentRepository;
import miljana.andric.repositories.SubjectRepository;
import miljana.andric.services.SubjectService;

@Service
public class SubjectServiceImpl implements SubjectService{

	@Autowired
	private SubjectRepository subjectRepository;
	@Autowired
	private SubjectMapper subjectMapper;
	@Autowired
	private ProfessorRepository professorRepository;
	@Autowired
	private StudentRepository studentRepository;
	
	
	@Override
	public List<SubjectEntity> findAllSubjects() {
		return subjectRepository.findAll();
	}

	@Override
	public List<SubjectEntity> findSubjectsByPage(int number) {
		Page<SubjectEntity> page = subjectRepository.findAll(
		PageRequest.of(number + 1, 2, Sort.by(Sort.Direction.ASC, "id")));
		return page.stream().collect(Collectors.toList());
	}

	@Override
	public Optional<SubjectEntity> findById(Long id) throws EngineeringException {
		Optional<SubjectEntity> subjectEntity = subjectRepository.findById(id);
        return subjectEntity;
	}

	@Override
	public SubjectDto addSubject(SubjectDto subject){
		return subjectMapper.toDto(subjectRepository.save(subjectMapper.toEntity(subject)));
	}

	@Override
	public void deleteSubject(Long id) throws EngineeringException {
		Optional<SubjectEntity> subjectEntity = subjectRepository.findById(id);
        if (subjectEntity.isEmpty()) {
            throw new EngineeringException(404, "Not found");
        }
        subjectRepository.delete(subjectEntity.get());
		
	}

	@Override
	public SubjectEntity editSubject(Long id, SubjectUpdateDto subjectUpdate) throws EngineeringException {
		
		subjectRepository.findById(id).ifPresent(subject -> {
			subject.setName(Optional.ofNullable(subjectUpdate.getName()).orElse(subject.getName()));
			subject.setDescription(Optional.ofNullable(subjectUpdate.getDescription()).orElse(subject.getDescription()));
			subject.setNoOfESP(Optional.ofNullable(subjectUpdate.getNoOfESP()).orElse(subject.getNoOfESP()));
			subject.setYearOfStudy(Optional.ofNullable(subjectUpdate.getYearOfStudy()).orElse(subject.getYearOfStudy()));
			subject.setSemester(Optional.ofNullable(subjectUpdate.getSemester()).orElse(subject.getSemester()));
			subjectRepository.save(subject);
		});
		
		Optional<SubjectEntity> updatedSubject = subjectRepository.findById(id);
		
		if (updatedSubject.isPresent()) {
			return updatedSubject.get();
		} else {
			throw new EngineeringException(404,"Subject not found with id: " + id);
		}
	}

	@Override
	public Page<SubjectEntity> findAll(Integer pageNo, Integer pageSize, String sortBy, String sortOrder) {
		Sort.Direction direction = "asc".equalsIgnoreCase(sortOrder) ? Sort.Direction.ASC : Sort.Direction.DESC;
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, sortBy));
		Page<SubjectEntity> entites = subjectRepository.findAll(pageable);
		return entites;
	}

	@Override
	public Page<SubjectEntity> search(Integer pageNo, Integer pageSize, String sortBy, String sortOrder, String keyword, boolean name, boolean description, boolean semester, boolean noOfESP, boolean yearOfStudy) {

		 	List<SubjectEntity> subjectEntities = subjectRepository.findAll();
		 	List<SubjectEntity> subjects =  new ArrayList<>();

		   if ((keyword != null && !keyword.isEmpty()) && (name == false && description == false && semester == false && noOfESP == false && yearOfStudy == false)) {
			   subjects.addAll(subjectEntities.stream().filter(subject -> 
		            subject.getName().toLowerCase().contains(keyword.toLowerCase()) ||
		            subject.getDescription().toLowerCase().contains(keyword.toLowerCase()) ||
		            subject.getSemester().toString().toLowerCase().contains(keyword.toLowerCase()) ||
		            String.valueOf(subject.getNoOfESP()).contains(keyword) ||
		            String.valueOf(subject.getYearOfStudy()).contains(keyword))
		            .collect(Collectors.toList()));
		    }

		    if (name) {
		    	subjects.addAll(subjectEntities.stream().filter(subject -> subject.getName().toLowerCase().contains(keyword.toLowerCase())).collect(Collectors.toList()));
		    }

		    if (description) {
		    	subjects.addAll(subjectEntities.stream().filter(subject -> subject.getDescription().toLowerCase().contains(keyword.toLowerCase())).collect(Collectors.toList()));
		    }

		    if (semester) {
		    	subjects.addAll(subjectEntities.stream().filter(subject -> subject.getSemester().toString().toLowerCase().contains(keyword.toLowerCase())).collect(Collectors.toList()));
		    }

		    if (noOfESP	) {
		    	subjects.addAll(subjectEntities.stream().filter(subject -> subject.getNoOfESP().toString().contains(keyword.toLowerCase())).collect(Collectors.toList()));
		    }

		    if (yearOfStudy	) {
		    	subjects.addAll(subjectEntities.stream().filter(subject -> subject.getYearOfStudy().toString().contains(keyword.toLowerCase())).collect(Collectors.toList()));
		    }
		    
		    
		    subjects.sort((s1, s2) -> {
		    	 int compareResult = 0;

		         if ("asc".equalsIgnoreCase(sortOrder)) {
		             if ("name".equalsIgnoreCase(sortBy)) {
		                 compareResult = s1.getName().compareTo(s2.getName());
		             }
		         } else if ("desc".equalsIgnoreCase(sortOrder)) {
		             if ("name".equalsIgnoreCase(sortBy)) {
		                 compareResult = s2.getName().compareTo(s1.getName());
		             }
		         }
		        return compareResult != 0 ? compareResult : s1.getId().compareTo(s2.getId());
		    });

		    int totalElements = subjects.size();

		    int fromIndex = pageNo * pageSize;
		    int toIndex = Math.min(fromIndex + pageSize, totalElements);

		    List<SubjectEntity> pagedSubjects = subjects.subList(fromIndex, toIndex);

		    Pageable pageable = PageRequest.of(pageNo , pageSize, Sort.by(sortOrder, sortBy));
		    return new PageImpl<>(pagedSubjects, pageable, totalElements);
	}
	
	

	@Override
	public List<SubjectEntity> availableSubjects(Long professorId) {
		ProfessorEntity professor = professorRepository.findById(professorId).get();
		List<SubjectEntity> subjects = professor.getSubjects();
		return subjects;
	}

	@Override
	public List<SubjectEntity> subjectsProfessorDontHave(Long professorId) {
		List<SubjectEntity> subjects = new ArrayList<SubjectEntity>();
		for(SubjectEntity subject: subjectRepository.findAll()) {
			if(!availableSubjects(professorId).contains(subject)) {
				subjects.add(subject);
			}
		}
		return subjects;
	}

	@Override
	public void removeSubjectFromProfessor(Long professorId, Long subjectId) {
		ProfessorEntity professor = professorRepository.findById(professorId).get();
		SubjectEntity subject = professor.getSubjects().stream().filter(s -> s.getId().equals(subjectId)).findFirst().get();
		professor.getSubjects().remove(subject);
        professorRepository.save(professor);
	}
	
	@Override
	public List<SubjectEntity> getStudentsSubjects(String indexNumber, Long indexYear) {
		 StudentEntity student = studentRepository.findByIndexNumberAndIndexYear(indexNumber, indexYear).get(); 
		 return student.getSubjects();
		
	}


}
