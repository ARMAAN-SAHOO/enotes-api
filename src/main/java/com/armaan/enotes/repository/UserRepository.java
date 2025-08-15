package com.armaan.enotes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.armaan.enotes.entity.User;

public interface UserRepository extends JpaRepository<User,Long>{

    Optional<User> findByUserName(String userName);

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

}
