package softuni.exam.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.domain.entity.Player;

import java.math.BigDecimal;
import java.util.Set;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    Set<Player> findByTeamNameOrderById(String teamName);

    Set<Player> findBySalaryGreaterThanOrderBySalaryDesc(BigDecimal minSalary);
}
