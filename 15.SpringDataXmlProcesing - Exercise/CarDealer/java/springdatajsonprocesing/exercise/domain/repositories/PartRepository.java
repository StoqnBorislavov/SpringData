package springdatajsonprocesing.exercise.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springdatajsonprocesing.exercise.domain.entities.Part;

@Repository
public interface PartRepository extends JpaRepository<Part, Long> {


}
