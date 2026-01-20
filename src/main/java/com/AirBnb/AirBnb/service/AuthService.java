package com.AirBnb.AirBnb.service;

import com.AirBnb.AirBnb.dto.LoginDto;
import com.AirBnb.AirBnb.dto.SignUpRequestDto;
import com.AirBnb.AirBnb.dto.UserDto;
import com.AirBnb.AirBnb.entity.User;
import com.AirBnb.AirBnb.entity.enums.Role;
import com.AirBnb.AirBnb.exception.ResourceNotFoundException;
import com.AirBnb.AirBnb.repository.UserRepository;
import com.AirBnb.AirBnb.security.JWTService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public AuthService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JWTService jwtService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public UserDto signUp(SignUpRequestDto signUpRequestDto)
    {
        User user=userRepository.findByEmail(signUpRequestDto.getEmail()).orElse(null);
        if(user!=null)
        {
            throw new RuntimeException("User is already present with same email id");
        }
        User newUser=modelMapper.map(signUpRequestDto,User.class);
        newUser.setRoles(Set.of(Role.GUEST));
        newUser.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
        newUser=userRepository.save(newUser);

        return modelMapper.map(newUser,UserDto.class);

    }
    public String[] login(LoginDto loginDto)
    {
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(),loginDto.getPassword()));
        User user=(User) authentication.getPrincipal();
        String arr[]=new String[2];
        arr[0]=jwtService.generateAccessToken(user);
        arr[1]= jwtService.generateRefreshToken(user);
        return arr;
    }
    public String refreshToken(String refreshToken)
    {
        Long id= jwtService.getUserIdFromToken(refreshToken);
        User user=userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found with id:"+id));
        return jwtService.generateRefreshToken(user);
    }
}
