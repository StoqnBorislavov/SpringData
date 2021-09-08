package springdatajsonprocesing.exercise.domain.repositories;

import org.springframework.boot.autoconfigure.web.servlet.JerseyApplicationPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springdatajsonprocesing.exercise.domain.entities.Supplier;

import java.util.Set;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

}
