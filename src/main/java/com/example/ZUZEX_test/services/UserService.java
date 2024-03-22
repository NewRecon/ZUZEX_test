package com.example.ZUZEX_test.services;

import com.example.ZUZEX_test.models.House;
import com.example.ZUZEX_test.models.User;
import org.springframework.expression.AccessException;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User getUser(Long id);
    User updateUser(User user);
    void deleteUser(Long id) throws AccessException;
    User getUser(String username);
    List<User> getUsersByHouseId(House house);
    void inviteUser(User user, Long houseId);
    void unInviteUser(User user);
}
