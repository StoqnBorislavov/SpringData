package springdatajsonprocesing.exercise.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springdatajsonprocesing.exercise.domain.entities.Car;

import java.util.Set;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    Set<Car> findAllByMakeOrderByModelAscTravelledDistanceDesc(String make);


}
