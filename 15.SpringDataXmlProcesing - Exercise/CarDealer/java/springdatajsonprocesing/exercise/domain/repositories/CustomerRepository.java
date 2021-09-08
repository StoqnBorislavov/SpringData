package springdatajsonprocesing.exercise.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import springdatajsonprocesing.exercise.domain.entities.Customer;

import java.util.Set;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {


    @Query("SELECT c FROM Customer c ORDER BY c.birthDate ASC, c.youngDriver DESC")
    Set<Customer> findAllAndSort();

    Set<Customer> getAllByOrderByBirthDateAscYoungDriverAsc();

    @Query("SELECT c FROM Customer c WHERE c.sales.size > 0")
    Set<Customer> findAllCustomersWithSales();
}
