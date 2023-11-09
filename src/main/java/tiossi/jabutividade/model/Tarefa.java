package tiossi.jabutividade.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Tarefa {
    @Id
    private String idTarefa;
    private String descricaoTarefa;
    private String idUsuario;
    private Boolean completa;

    // public Tarefa () {

    // }

    public String getIdTarefa() {
        return idTarefa;
    }

    public void setIdTarefa(String id) {
        this.idTarefa = id;
    }

    public String getDescricaoTarefa() {
        return descricaoTarefa;
    }

    public void setDescricaoTarefa(String descricao) {
        this.descricaoTarefa = descricao;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String id) {
        this.idUsuario = id;
    }

    public Boolean getCompleta() {
        return completa;
    }

    public void setCompleta(Boolean completa) {
        this.completa = completa;
    }
}
