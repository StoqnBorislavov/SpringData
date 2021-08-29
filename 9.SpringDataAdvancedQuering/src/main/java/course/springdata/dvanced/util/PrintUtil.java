package course.springdata.dvanced.util;

import course.springdata.dvanced.entity.Ingredient;
import course.springdata.dvanced.entity.Shampoo;

import java.util.stream.Collectors;

public class PrintUtil {
    public static void printShampoo(Shampoo s) {
        System.out.format("|%5d | %-30.30s | %-8.8s | %8.2f | %-40.40s | %-50.50s |%n",
                s.getId(), s.getBrand(), s.getSize(), s.getPrice(),
                s.getLabel().getTitle() + " - " + s.getLabel().getSubtitle(),
                s.getIngredients().stream().map(Ingredient::getName)
                        .collect(Collectors.joining(", ")));
    }

    public static void printIngredient(Ingredient s) {
        System.out.format("|%5d | %-30.30s | %8.2f |%n",
                s.getId(), s.getName(), s.getPrice());
    }
}
