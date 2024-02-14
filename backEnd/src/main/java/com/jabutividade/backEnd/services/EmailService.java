package com.jabutividade.backEnd.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    JavaMailSender mailSender;
    SimpleMailMessage mensagemEmail = new SimpleMailMessage();

    public void enviarCodigoVerificacaoEmail(String codigo, String email) {
        mensagemEmail.setSubject("Verificação de e-mail no Jabuti!");
        mensagemEmail.setText(codigo);
        mensagemEmail.setTo(email);
        mensagemEmail.setFrom("noreply@jabuti.com");

        mailSender.send(mensagemEmail);
    }
}
