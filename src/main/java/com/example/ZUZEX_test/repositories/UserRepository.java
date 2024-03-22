package com.example.ZUZEX_test.repositories;

import com.example.ZUZEX_test.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findUserByName(String name);
    public Optional<List<User>> findUsersByHouseId(Long id);
}
