package com.starez.HotelServer.Services;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.starez.HotelServer.Entity.User;
import com.starez.HotelServer.dto.Signup;
import com.starez.HotelServer.dto.UserDto;
import com.starez.HotelServer.enums.UserRole;
import com.starez.HotelServer.repository.UserRepository;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;

    @PostConstruct
    public void createAnAdminAccount() {
        Optional <User> adminAccount = userRepository.findByUserRole(UserRole.ADMIN);
        if (adminAccount.isEmpty()) {
            User admin = new User();
            admin.setName("Admin");
            admin.setEmail("admin@test.com");
            admin.setPassword(new BCryptPasswordEncoder().encode("admin"));  
            admin.setUserRole(UserRole.ADMIN);   
            userRepository.save(admin);
        }else{
            System.out.println("Admin account already exists");
        }
    }

    public UserDto createUser(Signup signup) {
        if (userRepository.findFirstByEmail(signup.getEmail()).isPresent()) {
            throw new EntityExistsException("User with email: " + signup.getEmail() + " already exists");   
        }
        User user = new User();
        user.setName(signup.getName());
        user.setEmail(signup.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(signup.getPassword()));
        user.setUserRole(UserRole.CUSTOMER);
        User savedUser = userRepository.save(user);
        return savedUser.getUserDto();
    }

}
