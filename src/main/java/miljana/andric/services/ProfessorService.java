package miljana.andric.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import miljana.andric.dtos.ProfessorRegisterDto;
import miljana.andric.dtos.ProfessorResponseDto;
import miljana.andric.dtos.ProfessorUpdateDto;
import miljana.andric.entities.ProfessorEntity;
import miljana.andric.entities.SubjectEntity;
import miljana.andric.exceptions.EngineeringException;

public interface ProfessorService {
	
	List<ProfessorResponseDto> findAllProfessors();
 	
 	List<ProfessorEntity> findProfessorsByPage(int number);

    Optional<ProfessorResponseDto> findById(Long id) throws EngineeringException;

    ProfessorResponseDto addProfessor(ProfessorRegisterDto professorRegister) throws EngineeringException;

    void deleteProfessor(Long id) throws EngineeringException;

    ProfessorResponseDto editProfessor(Long id, ProfessorUpdateDto professorRegister) throws EngineeringException;
    
    Page<ProfessorEntity> findAll(Integer pageNo, Integer pageSize, String sortBy, String sortOrder);
    
    Page<ProfessorEntity> search(Integer pageNo, Integer pageSize, String sortBy, String sortOrder, String keyword, boolean firstName, boolean lastName, boolean email, boolean address, boolean postalCode,
            boolean phone, boolean reelectionDate, boolean title);
    
    ProfessorEntity addSubjectsToProfessor(Long professorId, List<SubjectEntity> subjects) throws EngineeringException;
    
    List<ProfessorEntity> availableProfessors(Long subjectId);

}
