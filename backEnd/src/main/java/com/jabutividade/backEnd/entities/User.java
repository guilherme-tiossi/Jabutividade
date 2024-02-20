package com.jabutividade.backEnd.entities;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document(collection = "usuarios")
public class User {

    @Id
    private String id;
    private String username;
    private String email;
    private String password;
    private Boolean confirmedEmail;
    private String emailConfirmationCode;
    private Instant emailConfirmationCodeExpiration;
    private String urlProfilePicture;

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", confirmedEmail=" + confirmedEmail +
                ", emailConfirmationCode='" + emailConfirmationCode + '\'' +
                ", emailConfirmationCodeExpiration=" + emailConfirmationCodeExpiration + '\'' + 
                ", urlProfilePicture=" + urlProfilePicture +
                '}';
    }
}
