package springdatajsonprocesing.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springdatajsonprocesing.domain.entities.Product;

import java.math.BigDecimal;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Set<Product> findAllByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
}
