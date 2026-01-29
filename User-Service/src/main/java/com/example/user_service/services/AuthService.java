package com.example.user_service.services;

import com.example.user_service.dto.LoginRequestDto;
import com.example.user_service.dto.SignupRequestDto;
import com.example.user_service.dto.UserDto;
import com.example.user_service.entity.UserEntity;
import com.example.user_service.exceptions.BadRequestException;
import com.example.user_service.exceptions.ResourceNotFoundException;
import com.example.user_service.repository.UserRepository;
import com.example.user_service.utils.PasswordUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;
    private final JwtService jwtService;

    public UserDto signUp(SignupRequestDto signupRequestDto) {
        String email = signupRequestDto.getEmail();

        boolean exists = userRepository.existsByEmail(email);
        if (exists) throw new BadRequestException("User Already Exists with email : " + email);

        UserEntity user = modelMapper.map(signupRequestDto, UserEntity.class);
        user.setPassword(PasswordUtil.hashPassword(signupRequestDto.getPassword()));

        UserEntity savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    public String login(LoginRequestDto loginRequestDto) {
        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();

        UserEntity user = userRepository.findByEmail(email);
        if (user == null) throw new ResourceNotFoundException("User Not Found");

        boolean isPasswordMatched = PasswordUtil.checkPassword(password, user.getPassword());
        if (!isPasswordMatched) throw new BadRequestException("Password do not matched");

        return jwtService.generateAccessToken(user);
    }
}
