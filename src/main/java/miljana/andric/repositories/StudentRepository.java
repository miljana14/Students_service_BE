package miljana.andric.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import miljana.andric.entities.Index;
import miljana.andric.entities.StudentEntity;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Index>{
	
	Optional<StudentEntity> findByEmail(String email);
	
	Optional<StudentEntity> findByIndexNumberAndIndexYear(String indexNumber, Long indexYear);

}
