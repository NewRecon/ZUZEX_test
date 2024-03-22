package com.example.ZUZEX_test.services.impl;

import com.example.ZUZEX_test.models.House;
import com.example.ZUZEX_test.models.User;
import com.example.ZUZEX_test.repositories.UserRepository;
import com.example.ZUZEX_test.services.HouseService;
import com.example.ZUZEX_test.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.expression.AccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final HouseService houseService;

    @Override
    public User getUser(Long id) {

        return userRepository.findById(id).orElseThrow(()->new UsernameNotFoundException("user not found"));
    }

    @Override
    public User updateUser(User user) throws UsernameNotFoundException {
        User currentUser = getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        if (!currentUser.getName().equals(user.getName()))
            throw new UsernameNotFoundException("is not your account");

        if (user.getAge() != currentUser.getAge())
            currentUser.setAge(user.getAge());

        return userRepository.save(currentUser);
    }

    @Override
    public void deleteUser(Long id) throws UsernameNotFoundException, AccessException {
        User currentUser = getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        if (!currentUser.getId().equals(id))
            throw new UsernameNotFoundException("is not your account");


        List<House> houses = houseService.getHousesByOwnerId(currentUser);

        if (houses != null) {
            for (House houseIter: houses) {
                List<User> users = getUsersByHouseId(houseIter);
                if (users != null) {
                    for (User userIter : users) {
                        unInviteUser(userIter);
                    }
                }
                houseIter.setOwnerId(null);
                houseService.updateHouse(houseIter);
                houseService.deleteHouse(houseIter.getId());
            }
        }

        userRepository.deleteById(id);
    }

    @Override
    public User getUser(String name) {
        return userRepository.findUserByName(name).orElseThrow(()->new UsernameNotFoundException("user not found"));
    }

    @Override
    public List<User> getUsersByHouseId(House house) {
        return userRepository.findUsersByHouseId(house.getId()).orElseThrow(()->new UsernameNotFoundException("users not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByName(username).orElse(null);
    }

    @Override
    public void inviteUser(User user, Long houseId){
        user.setHouseId(houseId);
        userRepository.save(user);
    }

    @Override
    public void unInviteUser(User user){
        user.setHouseId(null);
        userRepository.save(user);
    }
}
