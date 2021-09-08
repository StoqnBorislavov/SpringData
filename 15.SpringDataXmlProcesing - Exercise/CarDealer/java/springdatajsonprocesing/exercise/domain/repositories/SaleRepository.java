package springdatajsonprocesing.exercise.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springdatajsonprocesing.exercise.domain.entities.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
}
