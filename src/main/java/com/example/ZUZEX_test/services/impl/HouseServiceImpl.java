package com.example.ZUZEX_test.services.impl;

import com.example.ZUZEX_test.models.House;
import com.example.ZUZEX_test.models.User;
import com.example.ZUZEX_test.repositories.HouseRepository;
import com.example.ZUZEX_test.services.HouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService {
    private final HouseRepository houseRepository;

    @Override
    public House addHouse(House house) {
        return houseRepository.save(house);
    }

    @Override
    public House getHouse(Long id) {
        return houseRepository.findById(id).orElseThrow(()->new RuntimeException("house not found"));
    }

    @Override
    public House updateHouse(House house){
        return houseRepository.save(house);
    }

    @Override
    public void deleteHouse(Long id) {
        houseRepository.deleteById(id);
    }

    @Override
    public List<House> getHousesByOwnerId(User user) {
        return houseRepository.findHousesByOwnerId(user.getId()).orElseThrow(()->new UsernameNotFoundException("houses not found"));
    }
}
