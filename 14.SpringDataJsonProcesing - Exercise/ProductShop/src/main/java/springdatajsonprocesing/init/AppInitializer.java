package springdatajsonprocesing.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import springdatajsonprocesing.services.CategoryService;
import springdatajsonprocesing.services.ProductService;
import springdatajsonprocesing.services.UserService;

@Component
public class AppInitializer implements CommandLineRunner {

    private final UserService userService;
    private final ProductService productService;
    private final CategoryService categoryService;

    @Autowired
    public AppInitializer(UserService userService, ProductService productService, CategoryService categoryService) {
        this.userService = userService;
        this.productService = productService;
        this.categoryService = categoryService;
    }



    @Override
    public void run(String... args) throws Exception {
        //System.out.println(this.userService.seedUsers());
        //System.out.println(this.categoryService.seedCategories());
        //System.out.println(this.productService.seedProducts());
        System.out.println(this.productService.findAllProductsWithPriceBetween500and1000());
    }
}
