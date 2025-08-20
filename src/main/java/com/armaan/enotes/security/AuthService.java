package com.armaan.enotes.security;

import java.time.Instant;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.armaan.enotes.dto.RefreshRequest;
import com.armaan.enotes.dto.TokensResponse;
import com.armaan.enotes.dto.UserDto;
import com.armaan.enotes.dto.UserResponse;
import com.armaan.enotes.entity.RefreshToken;
import com.armaan.enotes.entity.User;
import com.armaan.enotes.mapper.UserMapper;
import com.armaan.enotes.repository.RefreshTokenRepository;
import com.armaan.enotes.repository.UserRepository;
import com.armaan.enotes.security.jwt.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;


    public UserResponse signUp(UserDto userDto)
    {
        if(userRepository.existsByEmail(userDto.getEmail())){
            throw new RuntimeException("User Already Exists");
        }
        User user =userMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
        return userMapper.toResponse(user);
    }


    public TokensResponse signIn(UserDto userDto) throws AuthenticationException
    {


        //sesion-bases
       /* User user=userRepository.findByEmail(userDto.getEmail()).orElseThrow(
         ()->   new RuntimeException("Invalid credentials")
        );

        if(!passwordEncoder.matches(userDto.getPassword(), user.getPassword()))
        {
            throw new RuntimeException("Invalid credentials");
        }
        return userMapper.toResponse(user);*/

        
    // Load user entity
    User user = userRepository.findByUserName(userDto.getUserName())
            .orElseThrow(() -> new RuntimeException("User not found"));

    //jwt-based
        if(!passwordEncoder.matches(userDto.getPassword(), user.getPassword())){
                throw new RuntimeException("Invalid credentials");
        }

        Optional<RefreshToken> existingRefreshToken=refreshTokenRepository.findByUser(user)
        .filter(token->token.getExpiryDate().isAfter(Instant.now()));


    // Generate tokens
    String accessToken = jwtUtil.generateAccessToken(userDto.getUserName());
    String refreshToken;

    if(existingRefreshToken.isPresent()){
            refreshToken=existingRefreshToken.get().getToken();

    }else{
         refreshToken = jwtUtil.generateRefreshToken(user.getUserName());

         RefreshToken refreshTokenEntity=RefreshToken.builder()
         .token(refreshToken)
         .expiryDate(Instant.now().plusSeconds(jwtUtil.REFRESH_EXPIRATION / 1000))
         .build();

         refreshTokenRepository.save(refreshTokenEntity);
    }

    // Save refresh token with fixed expiry
    RefreshToken refreshTokenEntity = RefreshToken.builder()
            .token(refreshToken)
            .user(user)
            .expiryDate(Instant.now().plusSeconds(jwtUtil.REFRESH_EXPIRATION / 1000)) // make sure REFRESH_EXPIRATION is in ms
            .build();

    refreshTokenRepository.save(refreshTokenEntity);

    // Return both tokens
    return new TokensResponse(accessToken, refreshToken);

    }

    public TokensResponse refresh(RefreshRequest refreshRequest) {

    RefreshToken refreshToken = refreshTokenRepository.findByToken(refreshRequest.refreshToken())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid refresh token"));

    if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
        refreshTokenRepository.delete(refreshToken);
        // User must sign in again
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh token expired");
    }

    String userName = jwtUtil.extractUsername(refreshRequest.refreshToken());
    UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);

    if (!jwtUtil.validateToken(refreshRequest.refreshToken(), userDetails)) {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid refresh token");
    }

     String newAccessToken = jwtUtil.generateAccessToken(userName);

    return new TokensResponse(newAccessToken, refreshRequest.refreshToken()); // refresh token stays the same
}

}
