package com.armaan.enotes.service;

import java.util.List;
import com.armaan.enotes.dto.UserDto;
import com.armaan.enotes.dto.UserResponse;

public interface UserService {

    UserResponse createUser(UserDto userDto);

    // Read (active only)
    List<UserResponse> getAllUsers();

    UserResponse getUserById(Long id);

    UserResponse updateUser(Long id, UserDto userDto);

    UserResponse deleteUser(Long id);

}
