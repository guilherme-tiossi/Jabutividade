package tiossi.jabutividade.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Document
public class Usuario {
    @Id
    private String idUsuario;

    @NotBlank
    @Size(max = 20)
    private String usuario;

    @NotBlank
    @Size(max = 50)
    private String email;
    
    @NotBlank
    @Size(max = 120)
    private String password;
}
