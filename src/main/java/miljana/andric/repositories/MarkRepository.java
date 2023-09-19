package miljana.andric.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import miljana.andric.entities.ExamEntity;
import miljana.andric.entities.MarkEntity;
import miljana.andric.entities.StudentEntity;

@Repository
public interface MarkRepository extends JpaRepository<MarkEntity, Long>{
	
	List<MarkEntity> findByStudent(StudentEntity student);
	
	boolean existsByExam(ExamEntity exam);
	
	boolean existsByExamAndStudent(ExamEntity exam, StudentEntity student);


}
