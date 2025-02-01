package com.starez.HotelServer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.starez.HotelServer.Entity.User;

import com.starez.HotelServer.enums.UserRole;


@Repository
public interface UserRepository  extends JpaRepository<User, Long> {
    Optional <User> findFirstByEmail(String email);

    Optional <User> findByUserRole(UserRole userRole);
}
