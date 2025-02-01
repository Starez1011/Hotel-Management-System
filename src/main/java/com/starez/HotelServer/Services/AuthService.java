package com.starez.HotelServer.Services;

import com.starez.HotelServer.dto.Signup;
import com.starez.HotelServer.dto.UserDto;

public interface AuthService {
    UserDto createUser(Signup signup);
}
