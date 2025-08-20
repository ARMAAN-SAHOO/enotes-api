// 
package com.armaan.enotes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.armaan.enotes.dto.JwtResponse;
import com.armaan.enotes.dto.TokensResponse;
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

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody UserDto userDto) {
       
        try {
            TokensResponse tokensResponse =authService.signIn(userDto);
            return ResponseEntity.ok(new JwtResponse(tokensResponse.accessToken()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(401).body(new JwtResponse("Invalid username or password"));
        }
    }


}
