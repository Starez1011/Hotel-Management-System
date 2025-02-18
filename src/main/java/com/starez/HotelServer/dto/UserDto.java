package com.starez.HotelServer.dto;

import com.starez.HotelServer.enums.UserRole;

import lombok.Data;

@Data
public class UserDto {
    private Long id;

    private String email;

    private String name;

    private UserRole userRole;
}
