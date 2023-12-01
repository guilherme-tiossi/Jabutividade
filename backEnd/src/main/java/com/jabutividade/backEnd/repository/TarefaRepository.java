package com.jabutividade.backEnd.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jabutividade.backEnd.entities.Tarefa;

public interface TarefaRepository extends MongoRepository<Tarefa, String> {
    List<Tarefa> findByIdUsuarioOrderByCompleta(String idUsuario);
    List<Tarefa> findByIdTarefa(String idTarefa);
}
