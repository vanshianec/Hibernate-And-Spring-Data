package com.example.demo.repository;

import com.example.demo.domain.dto.CategoriesByProductsDto;
import com.example.demo.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findById(int id);

    List<Category> findAll();

    @Query("SELECT new com.example.demo.domain.dto.CategoriesByProductsDto(c.name, c.products.size, AVG(p.price), SUM(p.price)) " +
            "FROM Category AS c " +
            "JOIN c.products AS p " +
            "GROUP BY c.id " +
            "ORDER BY c.products.size DESC")
    List<CategoriesByProductsDto> getCategoriesByProducts();
}
