package com.example.demo.repository;

import com.example.demo.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findById(int id);

    @Query("SELECT u FROM User u " +
            "JOIN Product AS p " +
            "ON u.id = p.seller.id " +
            "WHERE p.buyer.id IS NOT NULL " +
            "GROUP BY u.id " +
            "ORDER BY u.lastName, u.firstName")
    List<User> getAllBySellContainsProductBuyer();

    @Query("SELECT u FROM User u " +
            "JOIN Product AS p " +
            "ON u.id = p.seller.id " +
            "GROUP BY u.id " +
            "ORDER BY u.productsSold.size DESC, u.lastName")
    List<User> getUsersAndProducts();
}
