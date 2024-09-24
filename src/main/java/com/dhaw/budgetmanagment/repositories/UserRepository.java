package com.dhaw.budgetmanagment.repositories;

import com.dhaw.budgetmanagment.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
}
