package com.starez.HotelServer.controller.auth;


import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.starez.HotelServer.Entity.User;
import com.starez.HotelServer.Services.AuthService;
import com.starez.HotelServer.Services.jwt.UserService;
import com.starez.HotelServer.dto.AuthRequest;
import com.starez.HotelServer.dto.AuthResponse;
import com.starez.HotelServer.dto.Signup;
import com.starez.HotelServer.dto.UserDto;
import com.starez.HotelServer.repository.UserRepository;
import com.starez.HotelServer.util.JwtUtil;

import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    private final UserService userService;
    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody Signup signup) {
        try{
            UserDto createdUser = authService.createUser(signup);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (EntityExistsException e) {
            return new ResponseEntity<>("User with email: " + signup.getEmail() + " already exists", HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            return new ResponseEntity<>("User not created, come agian later", HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/login")
    public AuthResponse createAuthToken(@RequestBody AuthRequest authRequest) {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        }catch(BadCredentialsException e){
            throw new BadCredentialsException("Invalid username or password");
        }
        final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(authRequest.getEmail());
        Optional <User> optionalUser = userRepository.findFirstByEmail(authRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails);

        AuthResponse authResponse = new AuthResponse();
        if(optionalUser.isPresent()){
            authResponse.setJwt(jwt);
            authResponse.setUserRole(optionalUser.get().getUserRole());
            authResponse.setUserId(optionalUser.get().getId());
        }
        return authResponse;
    }
}
