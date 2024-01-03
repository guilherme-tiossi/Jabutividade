package com.jabutividade.backEnd.entities;

import java.util.Objects;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tarefa {
    @Id
    private String idTarefa;
    private String descricaoTarefa;
    private String idUsuario;
    private Boolean completa;
    private Integer order;

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
                Objects.equals(completa, tarefa.completa) &&
                Objects.equals(order, tarefa.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTarefa, descricaoTarefa, idUsuario, completa);
    }

    @Override
    public String toString() {
        return "Tarefa{id=" + idTarefa + ", descricao='" + descricaoTarefa + "', order=" + order + ", completa=" + completa + ", idUsuário= " + idUsuario + "}";
    }

}
