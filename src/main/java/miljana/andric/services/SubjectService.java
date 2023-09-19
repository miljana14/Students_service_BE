package miljana.andric.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import miljana.andric.dtos.SubjectDto;
import miljana.andric.dtos.SubjectUpdateDto;
import miljana.andric.entities.SubjectEntity;
import miljana.andric.exceptions.EngineeringException;

public interface SubjectService {
	

	List<SubjectEntity> findAllSubjects();
 	
 	List<SubjectEntity> findSubjectsByPage(int number);

    Optional<SubjectEntity> findById(Long id) throws EngineeringException;

    SubjectDto addSubject(SubjectDto subject);

    void deleteSubject(Long id) throws EngineeringException;

    SubjectEntity editSubject(Long id, SubjectUpdateDto subject) throws EngineeringException;
    
    Page<SubjectEntity> findAll(Integer pageNo, Integer pageSize, String sortBy, String sortOrder);
    
    Page<SubjectEntity> search(Integer pageNo, Integer pageSize, String sortBy, String sortOrder, String keyword, boolean name, boolean description, boolean semester, boolean noOfESP, boolean yearOfStudy);
    
    List<SubjectEntity> availableSubjects(Long professorId);
    
    List<SubjectEntity> subjectsProfessorDontHave(Long professorId);
    
    void removeSubjectFromProfessor(Long professorId, Long subjectId);
    
    List<SubjectEntity> getStudentsSubjects(String indexNumber, Long indexYear);
    

}
