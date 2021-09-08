package springdatajsonprocesing.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springdatajsonprocesing.domain.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
