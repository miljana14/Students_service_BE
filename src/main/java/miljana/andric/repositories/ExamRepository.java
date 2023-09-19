package miljana.andric.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import miljana.andric.entities.ExamEntity;
import miljana.andric.entities.ExaminationPeriod;
import miljana.andric.entities.SubjectEntity;

@Repository
public interface ExamRepository extends JpaRepository<ExamEntity, Long>{
	
	boolean existsBySubjectAndExaminationPeriod(SubjectEntity subject, ExaminationPeriod examinationPeriod);
	
	List<ExamEntity> findByExaminationPeriod(ExaminationPeriod examinationPeriod);

}
