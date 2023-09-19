package miljana.andric.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import miljana.andric.entities.SubjectEntity;

@Repository
public interface SubjectRepository extends JpaRepository<SubjectEntity, Long>{


}
