package springdatajsonprocesing.exercise.services;

import springdatajsonprocesing.exercise.domain.entities.Supplier;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Set;

public interface SupplierService {
    void seedSupplier() throws IOException, JAXBException;
    String getAllLocalSuppliers();
    void findAllLocalSuppliers() throws JAXBException;
}
