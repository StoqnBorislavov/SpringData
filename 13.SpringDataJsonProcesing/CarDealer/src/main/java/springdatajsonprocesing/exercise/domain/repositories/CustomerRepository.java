package springdatajsonprocesing.exercise.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import springdatajsonprocesing.exercise.domain.entities.Customer;

import java.util.Set;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {


    Set<Customer> getAllByOrderByBirthdayAscYoungDriverAsc();
}
