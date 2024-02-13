package com.jabutividade.backEnd.controllers;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jabutividade.backEnd.config.UserAuthProvider;
import com.jabutividade.backEnd.dto.CredentialsDto;
import com.jabutividade.backEnd.dto.SignUpDto;
import com.jabutividade.backEnd.dto.UserDto;
import com.jabutividade.backEnd.services.UserService;
import com.jabutividade.utils.CodigoAleatorio;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
    private final UserAuthProvider userAuthProvider;

    @PostMapping("/login")
    public ResponseEntity<UserDto> login (@RequestBody CredentialsDto credentialsDto) {
        UserDto user = userService.login(credentialsDto);
        user.setToken(userAuthProvider.createToken(user));
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register (@RequestBody SignUpDto signUpDto) {
        UserDto user = userService.register(signUpDto);
        user.setToken(userAuthProvider.createToken(user));
        return ResponseEntity.created(URI.create("/users/" + user.getId())).body(user);
    }

    @GetMapping("/validar-token/{token}")
    public ResponseEntity<Authentication> validarToken(@PathVariable String token) {
        Authentication authentication = userAuthProvider.validateToken(token);    

        if (authentication != null) {
            return ResponseEntity.ok(authentication);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/enviar-codigo/{email}")
    public void enviarCodigoVerificacaoEmail(@PathVariable String email) {
        String codigo = CodigoAleatorio.gerarCodigo();
        System.out.println("------------------------------------------------\n código: " + codigo);
        System.out.println("------------------------------------------------\n email: " + email);
    }
}
