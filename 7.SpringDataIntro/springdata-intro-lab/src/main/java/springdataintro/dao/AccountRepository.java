package springdataintro.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import springdataintro.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
