// 
package com.armaan.enotes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.armaan.enotes.dto.UserDto;
import com.armaan.enotes.dto.UserResponse;
import com.armaan.enotes.security.AuthService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/auth")
public class AuthController {

    private final AuthService authService;

    // Signup endpoint (JSON)
    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup(@RequestBody UserDto userDto) {
        UserResponse userResponse = authService.signUp(userDto);
        return ResponseEntity.ok(userResponse);
    }

    // No need for login() here — Spring Security handles formLogin automatically
    // No need for logout() method — Spring Security handles it automatically
}
