package com.com.trading.PrachinX.service;

import com.com.trading.PrachinX.dto.request.LoginRequest;
import com.com.trading.PrachinX.dto.request.RegisterRequest;
import com.com.trading.PrachinX.dto.response.AuthResponse;
import com.com.trading.PrachinX.entity.User;
import com.com.trading.PrachinX.exception.CustomException;
import com.com.trading.PrachinX.repository.UserRepository;
import com.com.trading.PrachinX.security.JwtUtil;
import com.com.trading.PrachinX.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new CustomException(
                    "Email already exists", HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new CustomException(
                    "Username already exists", HttpStatus.BAD_REQUEST);
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .role(User.Role.USER)
                .build();

        User savedUser = userRepository.save(user);

        UserDetails userDetails = userDetailsService
                .loadUserByUsername(savedUser.getEmail());
        String token = jwtUtil.generateToken(userDetails);

        return AuthResponse.builder()
                .token(token)
                .email(savedUser.getEmail())
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .username(savedUser.getUsername())
                .role(savedUser.getRole().name())
                .message("Registration successful")
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new CustomException(
                                "User not found", HttpStatus.NOT_FOUND));

        UserDetails userDetails = userDetailsService
                .loadUserByUsername(user.getEmail());
        String token = jwtUtil.generateToken(userDetails);

        return AuthResponse.builder()
                .token(token)
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .role(user.getRole().name())
                .message("Login successful")
                .build();
    }
}