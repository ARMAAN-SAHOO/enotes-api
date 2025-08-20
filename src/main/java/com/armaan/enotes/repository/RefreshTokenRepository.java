package com.armaan.enotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.armaan.enotes.dto.UserDto;
import com.armaan.enotes.entity.RefreshToken;
import com.armaan.enotes.entity.User;

import java.util.Optional;


@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long>{

    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(UserDto userDto);
    Optional<RefreshToken> findByUser(User user);

}
