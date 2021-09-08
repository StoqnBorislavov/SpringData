package springdatajsonprocesing.exercise.services;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface CarService {
    void seedCars() throws Exception;

    String findByToyota();

    String getAllCarsWithParts();

    void findCarsAndParts() throws JAXBException;

    void getByMake() throws JAXBException;
}
