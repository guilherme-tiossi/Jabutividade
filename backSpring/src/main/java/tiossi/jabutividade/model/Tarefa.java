package tiossi.jabutividade.model;

import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Tarefa {
    @Id
    private String idTarefa;
    private String descricaoTarefa;
    private String idUsuario;
    private Boolean completa;

    public Tarefa() {
        this.descricaoTarefa = "";
        this.idUsuario = "";
        this.completa = false;
    }

    public Tarefa(String id, String descricao, String usuario, Boolean completa) {
        this.idTarefa = id;
        this.descricaoTarefa = descricao;
        this.idUsuario = usuario;
        this.completa = completa;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Tarefa tarefa = (Tarefa) o;
        return Objects.equals(idTarefa, tarefa.idTarefa) &&
                Objects.equals(descricaoTarefa, tarefa.descricaoTarefa) &&
                Objects.equals(idUsuario, tarefa.idUsuario) &&
                Objects.equals(completa, tarefa.completa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTarefa, descricaoTarefa, idUsuario, completa);
    }
}
