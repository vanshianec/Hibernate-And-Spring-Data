package com.example.demo.repository;

import com.example.demo.domain.entities.Ingredient;
import com.example.demo.domain.entities.Shampoo;
import com.example.demo.domain.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ShampooRepository extends JpaRepository<Shampoo, Long> {
    List<Shampoo> findAllBySizeOrderById(Size size);

    List<Shampoo> findAllBySizeOrLabel_IdOrderByPrice(Size size, long labelId);

    List<Shampoo> findAllByPriceIsGreaterThanOrderByPriceDesc(BigDecimal price);

    long countAllByPriceIsLessThan(BigDecimal price);

    @Query("SELECT s " +
            "FROM Shampoo AS s " +
            "JOIN s.ingredients AS i " +
            "WHERE i IN :ingredients")
    List<Shampoo> selectShampoosByIngredients(@Param("ingredients") List<Ingredient> ingredients);

    @Query("SELECT s " +
            "FROM Shampoo AS s " +
            "WHERE s.ingredients.size < :count")
    List<Shampoo> selectShampoosByIngredientsCount(@Param("count") int count);

    @Query("SELECT SUM(i.price) " +
            "FROM Shampoo AS s " +
            "JOIN s.ingredients AS i " +
            "WHERE s.brand = :brand")
    BigDecimal selectIngredientNameAndShampooBrandByName(@Param("brand") String brand);

}
