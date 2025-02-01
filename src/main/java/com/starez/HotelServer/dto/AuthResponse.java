package com.starez.HotelServer.dto;

import com.starez.HotelServer.enums.UserRole;

import lombok.Data;

@Data
public class AuthResponse {
    
    private String jwt;

    private Long userId;

    private UserRole userRole;
}
