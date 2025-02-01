package com.starez.HotelServer.Services.jwt;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    
    UserDetailsService userDetailsService();
}
