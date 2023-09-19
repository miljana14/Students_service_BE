package miljana.andric.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import miljana.andric.entities.CityEntity;

@Repository
public interface CityRepository extends JpaRepository<CityEntity, String>{

	Optional<CityEntity> findByName(String name);
}
