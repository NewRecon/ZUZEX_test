package com.example.ZUZEX_test.services;

import com.example.ZUZEX_test.models.House;
import com.example.ZUZEX_test.models.User;
import org.springframework.expression.AccessException;

import java.util.List;

public interface HouseService {
    House addHouse(House house);

    House getHouse(Long id);

    House updateHouse(House house) throws AccessException;

    void deleteHouse(Long id);

    List<House> getHousesByOwnerId(User user);

}
