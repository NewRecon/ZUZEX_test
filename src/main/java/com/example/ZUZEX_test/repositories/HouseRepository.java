package com.example.ZUZEX_test.repositories;

import com.example.ZUZEX_test.models.House;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HouseRepository extends JpaRepository<House, Long> {
    public Optional<List<House>> findHousesByOwnerId(Long id);
}
