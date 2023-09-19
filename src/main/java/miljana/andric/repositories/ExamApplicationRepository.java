package miljana.andric.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import miljana.andric.entities.ExamApplicationEntity;
import miljana.andric.entities.StudentEntity;

@Repository
public interface ExamApplicationRepository extends JpaRepository<ExamApplicationEntity, Long>{
	
	public List<ExamApplicationEntity> findByStudent(StudentEntity student);
}
