package com.armaan.enotes.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import com.armaan.enotes.dto.UserDto;
import com.armaan.enotes.dto.UserResponse;
import com.armaan.enotes.entity.User;
import com.armaan.enotes.mapper.UserMapper;
import com.armaan.enotes.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class UserServiceImpl implements UserService{


    @Autowired
    private final UserMapper userMapper;

    @Autowired
    private final UserRepository userRepository;

    @Override
    public UserResponse createUser(UserDto userDto) {
        User user=userMapper.toEntity(userDto);
        User savedUser=userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    @Override
    public List<UserResponse> getAllUsers() {

        List<User> users=userRepository.findAll();
        return userMapper.toResponseList(users);
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user=userRepository.findById(id).orElseThrow();
        return userMapper.toResponse(user);
    }

    @Override
    public UserResponse updateUser(Long id, UserDto userDto) {
       
        User user=userRepository.findById(id).orElseThrow();
        userMapper.updateEntityFromDto(userDto,user);
        return userMapper.toResponse(user);

    }

    @Override
    public UserResponse deleteUser(Long id) {

        User user=userRepository.findById(id).orElseThrow();
        
        userRepository.delete(user);

        return userMapper.toResponse(user);
    }
}
