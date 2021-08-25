package springdataintro.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import springdataintro.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
