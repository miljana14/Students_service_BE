package miljana.andric.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import miljana.andric.dtos.StudentRegisterDto;
import miljana.andric.dtos.StudentResponseDto;
import miljana.andric.dtos.StudentUpdateDto;
import miljana.andric.entities.StudentEntity;
import miljana.andric.exceptions.EngineeringException;

public interface StudentService {
	
	 	List<StudentResponseDto> findAllStudents();
	 	
	 	List<StudentResponseDto> findStudentsByPage(int number);

	    Optional<StudentResponseDto> findById(String indexNumber, Long indexYear) throws EngineeringException;

	    StudentResponseDto addStudent(StudentRegisterDto student) throws EngineeringException;

	    void deleteStudent(String indexNumber, Long indexYear) throws EngineeringException;

	    StudentResponseDto editStudent(String indexNumber, Long indexYear, StudentUpdateDto studentRegister) throws EngineeringException;
	    
	    Page<StudentResponseDto> findAll(Integer pageNo, Integer pageSize, String sortBy, String sortOrder);
	    
	    Page<StudentEntity> search(Integer pageNo, Integer pageSize, String sortBy, String sortOrder, String keyword, boolean indexNumber, boolean indexYear, boolean firstName, boolean lastName, boolean email, boolean address, boolean postalCode, boolean currentYearOfStudy);
	    
	    Page<StudentEntity> searchBySubject(Long subjectId, Integer pageNo, Integer pageSize, String sortBy, String sortOrder, String keyword,
				boolean indexNumber, boolean indexYear, boolean firstName, boolean lastName, boolean currentYearOfStudy);
	    
	    List<StudentEntity> getStudentsForExam(Long examId);
	    
	    Page<StudentEntity> getSubjectsStudentsPage(Integer pageNo, Integer pageSize, String sortBy, String sortOrder, Long id);

}
