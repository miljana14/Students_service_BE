package miljana.andric.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import miljana.andric.entities.ProfessorEntity;

@Repository
public interface ProfessorRepository extends JpaRepository<ProfessorEntity, Long>{
	
	Optional<ProfessorEntity> findByEmail(String email);

}
