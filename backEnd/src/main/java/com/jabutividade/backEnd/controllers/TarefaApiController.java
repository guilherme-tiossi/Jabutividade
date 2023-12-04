package com.jabutividade.backEnd.controllers;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jabutividade.backEnd.services.TarefaService;

import ch.qos.logback.classic.Logger;

import com.jabutividade.backEnd.entities.Tarefa;

@RestController
@RequestMapping("/api")
public class TarefaApiController {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(TarefaApiController.class);

    @Autowired
    private TarefaService tarefaService;

    @PostMapping
    public Tarefa criarTarefa(@RequestBody Tarefa tarefa) {
        return tarefaService.criarTarefa(tarefa);
    }

    @PutMapping("/editar")
    public Tarefa editarTarefa(@RequestBody Tarefa tarefa) {
        return tarefaService.editarTarefa(tarefa);
    }

    @DeleteMapping("/{idTarefa}")
    public ResponseEntity<Void> deletaTarefa(@PathVariable String idTarefa) {
        tarefaService.deletarTarefa(idTarefa);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/completarTarefa/{idTarefa}")
    public ResponseEntity<Void> completarTarefa(@PathVariable String idTarefa, @RequestBody Boolean completa) {
        logger.debug("Iniciando o método completarTarefa");
        tarefaService.completarTarefa(idTarefa, completa);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{idUsuario}")
    public List<Tarefa> listarTarefasPorUsuario(@PathVariable String idUsuario) {
        return tarefaService.listarTarefasPorUsuario(idUsuario);
    }
}
