package springdatajsonprocesing.services;

import java.io.IOException;

public interface ProductService {

    String seedProducts() throws IOException;

    String findAllProductsWithPriceBetween500and1000();
}
