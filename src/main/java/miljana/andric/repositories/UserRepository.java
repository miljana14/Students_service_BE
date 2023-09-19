package miljana.andric.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import miljana.andric.entities.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String>{
	
	@Query("SELECT u FROM UserEntity u WHERE (CONCAT(LOWER(u.firstName), "
			+ "LOWER(u.lastName),"
			+ " LOWER(u.username)) LIKE LOWER(CONCAT('%', ?1,'%')))")
	public List<UserEntity> search(String keyword);
	
	Optional<UserEntity> findByEmail(String email);

	
}
