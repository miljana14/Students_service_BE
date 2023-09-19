package miljana.andric.repositories;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import miljana.andric.entities.ExaminationPeriod;

@Repository
public interface ExaminationPeriodRepository extends JpaRepository<ExaminationPeriod, Long>{
	
	Optional<ExaminationPeriod> findByName(String name);
	
	boolean existsByActiveTrue();
	
	boolean existsByBeginDateLessThanEqualAndEndDateGreaterThanEqual(LocalDate endDate, LocalDate beginDate);
	
	boolean existsByName(String name);
	
	Optional<ExaminationPeriod> findByActiveTrue();
}
