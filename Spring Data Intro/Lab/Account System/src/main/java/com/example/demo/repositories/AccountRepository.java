package com.example.demo.repositories;

import com.example.demo.models.Account;
import com.example.demo.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface AccountRepository extends CrudRepository<Account, Long> {
    Account getByUser(User user);
}
