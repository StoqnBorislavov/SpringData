package course.springdata.dvanced.init;

import course.springdata.dvanced.dao.IngredientRepository;
import course.springdata.dvanced.dao.LabelRepository;
import course.springdata.dvanced.dao.ShampooRepository;
import course.springdata.dvanced.entity.Ingredient;
import course.springdata.dvanced.entity.Shampoo;
import course.springdata.dvanced.util.PrintUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static course.springdata.dvanced.entity.Size.MEDIUM;

@Component
public class AppInitializer implements CommandLineRunner {

    private final ShampooRepository shampooRepository;
    private final LabelRepository labelRepository;
    private final IngredientRepository ingredientRepository;

    @Autowired
    public AppInitializer(ShampooRepository shampooRepository, LabelRepository labelRepository, IngredientRepository ingredientRepository) {
        this.shampooRepository = shampooRepository;
        this.labelRepository = labelRepository;
        this.ingredientRepository = ingredientRepository;
    }


    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Fetch Shampoos by size
        shampooRepository.findBySizeOrderById(MEDIUM).forEach(PrintUtil::printShampoo);

        System.out.println();
        System.out.println("------------------------------");
        System.out.println();

        // Fetch Shampoos by ingredients in List
        shampooRepository.findWithIngredientsIn(Set.of("Berry", "Mineral-Collagen"))
                .forEach(PrintUtil::printShampoo);

        System.out.println();
        System.out.println("------------------------------");
        System.out.println();

        // Fetch Shampoos by size and label
        shampooRepository.findBySizeOrLabelOrderByPriceDesc(MEDIUM, labelRepository.findByTitle("Vital").get()).forEach(PrintUtil::printShampoo);

        System.out.println();
        System.out.println("------------------------------");
        System.out.println();

        // Fetch Shampoos by price
        shampooRepository.findByPriceGreaterThanEqual(7.00).forEach(PrintUtil::printShampoo);

        System.out.println();
        System.out.println("------------------------------");
        System.out.println();

        // Fetch Shampoos by price between min and max price
        shampooRepository.findByPriceBetween(5.00,8.00).forEach(PrintUtil::printShampoo);

        System.out.println();
        System.out.println("------------------------------");
        System.out.println();

        // Fetch Ingredients by price
        ingredientRepository.findByNameIn(Set.of("Lavender", "Herbs", "Apple"))
                .forEach(PrintUtil::printIngredient);

        System.out.println();
        System.out.println("------------------------------");
        System.out.println();

        // Fetch shampoos count with price below given price
        System.out.printf("Number of shampoos with price less than %.2f is: %d%n",8.50 ,shampooRepository.countShampoosByPriceLessThan(8.50));

        System.out.println();
        System.out.println("------------------------------");
        System.out.println();

        // Fetch shampoos by ingredients count
        shampooRepository.findByCountOfIngredientsLowerThan(2)
                .forEach(PrintUtil::printShampoo);

        System.out.println();
        System.out.println("------------------------------");
        System.out.println();

        // Delete ingredients by name
        String nameToDelete = "Macadamia Oil";
        Ingredient ingredientToDelete = ingredientRepository.findByName(nameToDelete).get();
        List<Shampoo> byIngredient = shampooRepository.findByIngredient(ingredientToDelete);
        byIngredient.forEach(PrintUtil::printShampoo);
        byIngredient.forEach(shampoo -> shampoo.getIngredients().remove(ingredientToDelete));
        System.out.printf("Number of deleted ingredients with name %s is: %d%n",nameToDelete ,ingredientRepository.deleteAllByName(nameToDelete));

        System.out.println();

        System.out.println("------------------------------");
        System.out.println();

        // Update price of ingredients in list
        ingredientRepository.findAll().forEach(PrintUtil::printIngredient);
        System.out.printf("Number of ingredients updated: %d\n\nAfter update:\n",
                ingredientRepository.updatePriceOfIngredientsInList(Set.of("Lavender", "Herbs", "Apple"), 1.2));
        ingredientRepository.findAll().forEach(PrintUtil::printIngredient);
    }
}
