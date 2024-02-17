package com.jabutividade.backEnd.services;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jabutividade.backEnd.entities.User;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final UserService userService;

    public EmailService(JavaMailSender mailSender, UserService userService) {
        this.mailSender = mailSender;
        this.userService = userService;
    }

    public Map<String, Object> enviarCodigoVerificacaoEmail(String codigo, String email) {
        Map<String, Object> response = new HashMap<>();

        User usuario = null;

        try {
            usuario = userService.findByEmail(email.replaceAll("\"", "")); 
        } catch (UsernameNotFoundException e) {
            e.printStackTrace(); 
            response.put("message", "Usuário não encontrado para o e-mail " + email + ".");
            return response;
        }

        if (usuario == null) {
            response.put("message", "Usuário não encontrado para o e-mail " + email + ".");
            return response;
        }

        StringBuilder emailContent = new StringBuilder();
        emailContent.append("Agradecemos por iniciar o processo de confirmação de e-mail. Para completar a verificação, por favor utilize o seguinte código de verificação:\n\n");
        emailContent.append("Código de Verificação: ").append(codigo).append("\n\n");
        emailContent.append("Por favor, insira este código no local indicado na página de confirmação para validar seu endereço de e-mail:\n\n");
        emailContent.append("Se você não solicitou esta verificação, por favor, ignore este e-mail. A segurança da sua conta é importante para nós.\n\n");
        emailContent.append("Se precisar de assistência adicional ou tiver alguma dúvida, não hesite em nos contatar.\n");
        emailContent.append("Atenciosamente,\nEquipe do Jabutividade");        

        SimpleMailMessage mensagemEmail = new SimpleMailMessage();
        mensagemEmail.setSubject("Código de verificação de e-mail no Jabuti");
        mensagemEmail.setText(emailContent.toString());
        mensagemEmail.setTo(email);
        mensagemEmail.setFrom("noreply@jabuti.com");

        mailSender.send(mensagemEmail);
        usuario.setEmailConfirmationCode(codigo);
        usuario.setEmailConfirmationCodeExpiration(Instant.now().plusSeconds(900));
        userService.editUser(usuario).toString();

        response.put("success", true);
        return response;
    }

    public Map<String, Object> verificarCodigoVerificacaoEmail(String codigo, String email) {
        Map<String, Object> response = new HashMap<>();

        try {
            User usuario = userService.findByEmail(email.replaceAll("\"", "")); 
            if (usuario == null) {
                response.put("message", "Usuário não encontrado para o e-mail " + email + ".");
                return response;
            }

            if (usuario.getEmailConfirmationCodeExpiration().isBefore(Instant.now())) {
                response.put("message", "Código expirado! Tente novamente.");
                return response;
            }
    
            if (!codigo.equals(usuario.getEmailConfirmationCode())) {
                response.put("message", "Código inválido! Tente novamente.");
                return response;
            }

            usuario.setEmailConfirmationCode(null);
            usuario.setEmailConfirmationCodeExpiration(null);
            usuario.setConfirmedEmail(true);

            if (userService.editUser(usuario).getConfirmedEmail()) {
                response.put("success", true);
                return response;
            } else {
                response.put("message", "Erro ao confirmar e-mail, tente novamente.");
            }
        } catch (UsernameNotFoundException e) {
            response.put("success", false);
            response.put("message", "Usuário não encontrado para o e-mail " + email + ".");
        }
        
        return response;
    }
}