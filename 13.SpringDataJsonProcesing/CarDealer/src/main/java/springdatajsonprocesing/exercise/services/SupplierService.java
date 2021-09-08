package springdatajsonprocesing.exercise.services;

import springdatajsonprocesing.exercise.domain.entities.Supplier;

import java.io.IOException;
import java.util.Set;

public interface SupplierService {
    void seedSupplier() throws IOException;
    String getAllLocalSuppliers();
}
