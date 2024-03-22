package com.example.ZUZEX_test.services;

import com.example.ZUZEX_test.dto.AuthenticationRequest;
import com.example.ZUZEX_test.dto.AuthenticationResponse;
import com.example.ZUZEX_test.dto.RegisterRequest;
import com.example.ZUZEX_test.models.User;
import com.example.ZUZEX_test.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final PasswordEncoder encoder;
    private final UserRepository repository;
    private final JwtService service;
    private final AuthenticationManager manager;

    public AuthenticationResponse register(RegisterRequest request){
        var user = User.builder().name(request.getName()).age(request.getAge()).password(encoder.encode(request.getPassword())).build();
        repository.save(user);
        var jwtToken = service.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        manager.authenticate(new UsernamePasswordAuthenticationToken(request.getName(),request.getPassword()));
        var user = repository.findUserByName(request.getName()).orElseThrow(()->new UsernameNotFoundException("user not found"));
        var jwtToken = service.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
