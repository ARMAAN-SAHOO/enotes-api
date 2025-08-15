package com.armaan.enotes.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.armaan.enotes.dto.UserDto;
import com.armaan.enotes.dto.UserResponse;
import com.armaan.enotes.entity.User;
import com.armaan.enotes.mapper.UserMapper;
import com.armaan.enotes.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;


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


    public UserResponse signIn(UserDto userDto)
    {
        User user=userRepository.findByEmail(userDto.getEmail()).orElseThrow(
         ()->   new RuntimeException("Invalid credentials")
        );

        if(!passwordEncoder.matches(userDto.getPassword(), user.getPassword()))
        {
            throw new RuntimeException("Invalid credentials");
        }
        return userMapper.toResponse(user);
    }
}
