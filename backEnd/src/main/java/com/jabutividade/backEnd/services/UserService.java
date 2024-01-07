package com.jabutividade.backEnd.services;

import java.nio.CharBuffer;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jabutividade.backEnd.dto.CredentialsDto;
import com.jabutividade.backEnd.dto.SignUpDto;
import com.jabutividade.backEnd.dto.UserDto;
import com.jabutividade.backEnd.entities.User;
import com.jabutividade.backEnd.exceptions.AppException;
import com.jabutividade.backEnd.mappers.UserMapper;
import com.jabutividade.backEnd.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserDto login(CredentialsDto credentialsDto) {
        User user = userRepository.findByUsernameOrEmail(credentialsDto.login())
            .orElseThrow(() -> new AppException("Usuário desconhecido", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.password()),
            user.getPassword())) {
                return userMapper.toUserDto(user);
            }
            throw new AppException("Senha inválida", HttpStatus.BAD_REQUEST);
    }

    public UserDto register(SignUpDto signUpDto) {
        Optional<User> oUser = userRepository.findByUsername(signUpDto.username());
        Optional<User> oUserEmail = userRepository.findByEmail(signUpDto.email());

        if (oUser.isPresent() || oUserEmail.isPresent()) {
            throw new AppException("Usuário já existe", HttpStatus.BAD_REQUEST);
        }

        User user = userMapper.signUpToUser(signUpDto);

        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(signUpDto.password())));
        User savedUser = userRepository.save(user);
        return userMapper.toUserDto(savedUser);
    }

    public UserDto findByUsername(String login) {
        User user = userRepository.findByUsername(login)
                .orElseThrow(() -> new AppException("Usuário desconhecido", HttpStatus.NOT_FOUND));
        return userMapper.toUserDto(user);
    }

}
