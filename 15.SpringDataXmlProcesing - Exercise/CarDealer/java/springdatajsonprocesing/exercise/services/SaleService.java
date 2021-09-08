package springdatajsonprocesing.exercise.services;

import javax.xml.bind.JAXBException;

public interface SaleService {
    void seedSales();
    String getAllSales();

    void salesDiscount() throws JAXBException;
}
