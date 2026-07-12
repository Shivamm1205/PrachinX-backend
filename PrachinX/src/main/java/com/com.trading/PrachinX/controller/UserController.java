package com.com.trading.PrachinX.controller;

import com.com.trading.PrachinX.dto.response.ApiResponse;
import com.com.trading.PrachinX.entity.User;
import com.com.trading.PrachinX.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<User>> getCurrentUser(
            @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByEmail(
                userDetails.getUsername());
        return ResponseEntity.ok(
                ApiResponse.success("User fetched", user));
    }

    @PutMapping("/me")
    public ResponseEntity<ApiResponse<User>> updateCurrentUser(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody User updatedUser) {
        User user = userService.getUserByEmail(
                userDetails.getUsername());
        User updated = userService.updateUser(
                user.getId(), updatedUser);
        return ResponseEntity.ok(
                ApiResponse.success("User updated", updated));
    }
}
