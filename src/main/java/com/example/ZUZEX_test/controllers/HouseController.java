package com.example.ZUZEX_test.controllers;

import com.example.ZUZEX_test.models.House;
import com.example.ZUZEX_test.models.User;
import com.example.ZUZEX_test.services.HouseService;
import com.example.ZUZEX_test.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.rmi.AccessException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/houses")
public class HouseController {
    private final HouseService houseService;
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<House> getHouse(@PathVariable Long id){
        return ResponseEntity.ok(houseService.getHouse(id));
    }

    @PostMapping
    public ResponseEntity<House> addHouse(@RequestBody House house){
        User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.ok(houseService.addHouse(House.builder().address(house.getAddress()).ownerId(user.getId()).build()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> inviteUserInHouse(@RequestBody User user, @PathVariable Long id) throws UsernameNotFoundException{
        House house = houseService.getHouse(id);
        User owner = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        if (!house.getOwnerId().equals(owner.getId()))
            throw new UsernameNotFoundException("is not owner");

        User guest = userService.getUser(user.getUsername());
        guest.setHouseId(house.getId());
        userService.inviteUser(guest, id);

        return ResponseEntity.ok("add user "+guest.getName()+" in your house");

    }

    @PutMapping
    public ResponseEntity<House> updateHouse(@RequestBody House house) throws UsernameNotFoundException, AccessException, org.springframework.expression.AccessException {
        User currentUser = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        House currentHouse = houseService.getHouse(house.getId());

        if (!currentUser.getId().equals(currentHouse.getOwnerId()))
            throw new AccessException("Is not your house");

        if (!house.getAddress().equals(currentHouse.getAddress())){
            currentHouse.setAddress(house.getAddress());
        }

        if (house.getOwnerId() != null && !house.getOwnerId().equals(currentHouse.getOwnerId())){

            // проверяем, чтобы не выкинуло исключение. Существует ли новый владелец?
            User userOwner = userService.getUser(house.getOwnerId());

            currentHouse.setOwnerId(house.getOwnerId());
        }

        return ResponseEntity.ok(houseService.updateHouse(currentHouse));
    }

    // test
    @DeleteMapping("/{id}")
    public void deleteHouse(@PathVariable Long id) throws RuntimeException{
        House house = houseService.getHouse(id);
        User owner = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        if (!house.getOwnerId().equals(owner.getId()))
            throw new UsernameNotFoundException("is not owner");

        List<User> users = userService.getUsersByHouseId(house);

        if (users != null){
            for (User user: users) {
                userService.unInviteUser(user);
            }
        }
        houseService.deleteHouse(house.getId());
    }

}
