package springdatajsonprocesing.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springdatajsonprocesing.domain.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
