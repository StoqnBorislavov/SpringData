package springdatajsonprocesing.services;


import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springdatajsonprocesing.domain.dtos.ProductSeedDto;
import springdatajsonprocesing.domain.dtos.ProductsWithPriceDto;
import springdatajsonprocesing.domain.entities.Category;
import springdatajsonprocesing.domain.entities.Product;
import springdatajsonprocesing.domain.entities.User;
import springdatajsonprocesing.domain.repositories.CategoryRepository;
import springdatajsonprocesing.domain.repositories.ProductRepository;
import springdatajsonprocesing.domain.repositories.UserRepository;
import springdatajsonprocesing.utils.ValidatorUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
public class ProductsServiceImpl implements ProductService{

    private static final String PRODUCTS_PATH = "src/main/resources/json/products.json";
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidatorUtil validatorUtil;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductsServiceImpl(ProductRepository productRepository, ModelMapper modelMapper, Gson gson, ValidatorUtil validatorUtil, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validatorUtil = validatorUtil;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public String seedProducts() throws IOException {
        String content = String.join("", Files.readAllLines(Path.of(PRODUCTS_PATH)));

        ProductSeedDto[] productSeedDtos = this.gson.fromJson(content, ProductSeedDto[].class);

        StringBuilder sb = new StringBuilder();
        for (ProductSeedDto productSeedDto : productSeedDtos) {
            productSeedDto.setSeller(getRandomSeller());
            if(getRandomBuyerOrNot() == 1){
                productSeedDto.setBuyer(getRandomBuyer());
            }
            if(this.validatorUtil.isValid(productSeedDto)){
                Product product = this.modelMapper.map(productSeedDto, Product.class);
                product.setCategories(getRandomCategories());
                this.productRepository.saveAndFlush(product);
                sb.append(String.format("Correctly added product with name %s%n",product.getName()));
            }else {
                this.validatorUtil.violations(productSeedDto)
                        .forEach(v -> sb.append(v.getMessage()).append(System.lineSeparator()));
            }
        }
        return sb.toString();
    }

    @Override
    public String findAllProductsWithPriceBetween500and1000() {
        Set<Product> allProducts = this.productRepository.findAllByPriceBetween(BigDecimal.valueOf(500), BigDecimal.valueOf(1000));
        List<ProductsWithPriceDto> products = new ArrayList<>();
        for (Product product : allProducts) {
            ProductsWithPriceDto productsWithPriceDto = this.modelMapper.map(product, ProductsWithPriceDto.class);
            productsWithPriceDto.setSeller(product.getSeller().getFirstName() + " " + product.getSeller().getLastName());
            products.add(productsWithPriceDto);
        }
        return this.gson.toJson(products);
    }

    private Set<Category> getRandomCategories() {
        Random random =  new Random();
        int bound = random.nextInt(2);
        Set<Category> toReturn = new HashSet<>();
        for (int i = 0; i < bound; i++) {
            int id = random.nextInt((int)this.categoryRepository.count()) + 1;
            toReturn.add(this.categoryRepository.getById((long)id));
        }
        return toReturn;
    }

    private User getRandomBuyer() {
        Random random = new Random();
        int id = random.nextInt((int)this.userRepository.count()) + 1;
        return userRepository.getById((long)id);
    }

    private int getRandomBuyerOrNot() {
        Random random = new Random();
        int bool = random.nextInt(2);
        return bool;
    }

    private User getRandomSeller() {
        Random random = new Random();
        int id = random.nextInt((int)this.userRepository.count()) + 1;
        return userRepository.getById((long)id);
    }
}
