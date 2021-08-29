package course.springdata.dvanced.dao;

import course.springdata.dvanced.entity.Ingredient;
import course.springdata.dvanced.entity.Shampoo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    List<Ingredient> findByNameIn(Iterable<String> names);

    Optional<Ingredient> findByName(String name);

    @Transactional
    int deleteAllByName(String name);

    @Modifying
    @Transactional(propagation = Propagation.REQUIRED)
    @Query("UPDATE Ingredient i SET i.price = i.price * :percentage WHERE i.name IN :ingredient_names")
    int updatePriceOfIngredientsInList(@Param("ingredient_names") Iterable<String> ingredient_names,
                                       @Param("increasePercentage") double percentage);

}