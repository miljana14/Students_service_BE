package miljana.andric.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import miljana.andric.entities.TitleEntity;
import miljana.andric.entities.TitleEnum;

@Repository
public interface TitleRepository extends JpaRepository<TitleEntity, Long>{

	Optional<TitleEntity> findByTitle(TitleEnum title);
	
	boolean existsByTitle(TitleEnum title);
}
