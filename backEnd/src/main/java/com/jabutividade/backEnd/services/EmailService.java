package com.jabutividade.backEnd.services;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jabutividade.backEnd.entities.User;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final UserService userService;

    @Autowired
    public EmailService(JavaMailSender mailSender, UserService userService) {
        this.mailSender = mailSender;
        this.userService = userService;
    }


    public void enviarCodigoVerificacaoEmail(String codigo, String email) {
        StringBuilder emailContent = new StringBuilder();
        emailContent.append("Agradecemos por iniciar o processo de confirmação de e-mail. Para completar a verificação, por favor utilize o seguinte código de verificação:\n\n");
        emailContent.append("Código de Verificação: ").append(codigo).append("\n\n");
        emailContent.append("Por favor, insira este código no local indicado na página de confirmação para validar seu endereço de e-mail:\n\n");
        emailContent.append("Se você não solicitou esta verificação, por favor, ignore este e-mail. A segurança da sua conta é importante para nós.\n\n");
        emailContent.append("Se precisar de assistência adicional ou tiver alguma dúvida, não hesite em nos contatar.\n");
        emailContent.append("Atenciosamente,\nEquipe do Jabutividade");        

        SimpleMailMessage mensagemEmail = new SimpleMailMessage();
        mensagemEmail.setSubject("Còdigo de verificação de e-mail no Jabuti");
        mensagemEmail.setText(emailContent.toString());
        mensagemEmail.setTo(email);
        mensagemEmail.setFrom("noreply@jabuti.com");

        mailSender.send(mensagemEmail);

        User usuario = null;

        try {
            usuario = userService.findByEmail(email); 
        } catch (UsernameNotFoundException e) {
            e.printStackTrace(); 
        }
        
        if (usuario != null) {
            usuario.setEmailConfirmationCode(codigo);
            usuario.setEmailConfirmationCodeExpiration(Instant.now().plusSeconds(900));
            userService.editUser(usuario).toString();
        } else {
            System.out.println("Usuário não encontrado para o email: " + email);
        }
    }

    public void verificarCodigoVerificacaoEmail(String codigo, String email) {
        User usuario = null;
        try {
            usuario = userService.findByEmail(email.replaceAll("\"", "")); 
        } catch (UsernameNotFoundException e) {
            e.printStackTrace(); 
        }
        
        if (usuario != null) {
            // Check if the email confirmation code is expired
            if (usuario.getEmailConfirmationCodeExpiration().isBefore(Instant.now())) {
                System.out.println("Código expirado! Tente novamente");
                return;
            }
    
            // Compare strings using equals() method
            if (!codigo.equals(usuario.getEmailConfirmationCode())) {
                System.out.println("Código inválido! Tente novamente");
                return;
            }
    
            // Update user's email confirmation status
            usuario.setEmailConfirmationCode(null);
            usuario.setEmailConfirmationCodeExpiration(null);
            usuario.setConfirmedEmail(true);
    
            userService.editUser(usuario);
        } else {
            System.out.println("Usuário não encontrado para o email: '" + email+"'");
        }
    }
}




