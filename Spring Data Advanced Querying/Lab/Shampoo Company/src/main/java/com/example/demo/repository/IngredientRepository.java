package com.example.demo.repository;

import com.example.demo.domain.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    List<Ingredient> findAllByNameStartingWith(String pattern);

    List<Ingredient> findAllByNameInOrderByPrice(List<String> names);

    Ingredient getByName(String name);

    @Modifying
    @Transactional
    @Query("DELETE FROM Ingredient AS i WHERE i.name = :name")
    void deleteIngredientByName(@Param("name") String name);

    @Modifying
    @Transactional
    @Query("UPDATE Ingredient AS i SET i.price = i.price * 1.10")
    void updateIngredientsPriceByTenPercent();

    @Modifying
    @Transactional
    @Query("UPDATE Ingredient AS i SET i.price = i.price * 1.10 WHERE i.name IN :ingredientsNames")
    void updateIngredientsPriceByTenPercentByName(List<String> ingredientsNames);
}
