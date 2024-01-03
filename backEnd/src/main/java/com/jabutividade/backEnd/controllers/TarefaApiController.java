package com.jabutividade.backEnd.controllers;

import java.util.List;
import java.util.Optional;

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
        tarefa.setOrder(tarefaService.getOrderTarefa(tarefa.getIdUsuario()) + 1);
        return tarefaService.criarTarefa(tarefa);
    }

    @PutMapping("/editar")
    public Tarefa editarTarefa(@RequestBody Tarefa tarefa) {
        return tarefaService.editarTarefa(tarefa);
    }

    @PutMapping("/aumentarOrderTarefa/{idUsuario}")
    public ResponseEntity<Void> aumentarOrderTarefa(@PathVariable String idUsuario, @RequestBody Integer order) {
        
        logger.debug("1");
        List<Tarefa> tarefasUsuario = tarefaService.listarTarefasPorUsuario(idUsuario);
        Integer orderMaiorPrioridade = order - 1;

        logger.debug("2");
        Optional<Tarefa> tarefaAAumentarOptional = tarefasUsuario.stream()
            .filter(tarefa -> order.equals(tarefa.getOrder()))
            .findFirst();
        logger.debug("3");
        Optional<Tarefa> tarefaADiminuirOptional = tarefasUsuario.stream()
            .filter(tarefa -> orderMaiorPrioridade.equals(tarefa.getOrder()))
            .findFirst();

        logger.debug("4");
        Tarefa tarefaAAumentar = tarefaAAumentarOptional.get();
        Tarefa tarefaADiminuir = tarefaADiminuirOptional.get();
        
        logger.debug("5");
        tarefaAAumentar.setOrder(orderMaiorPrioridade);
        tarefaADiminuir.setOrder(order); // order de menor prioridade
        
        logger.debug("6");
        tarefaService.editarTarefa(tarefaAAumentar);
        tarefaService.editarTarefa(tarefaADiminuir);
        
        logger.debug("7"); 
        return ResponseEntity.ok().build();
    }

    @PutMapping("/abaixarOrderTarefa/{idUsuario}")
    public ResponseEntity<Void> abaixarOrderTarefa(@PathVariable String idUsuario, @RequestBody Integer order) {
        
        logger.debug("1");
        List<Tarefa> tarefasUsuario = tarefaService.listarTarefasPorUsuario(idUsuario);
        Integer orderMenorPrioridade = order + 1;

        logger.debug("2");
        Optional<Tarefa> tarefaAAumentarOptional = tarefasUsuario.stream()
            .filter(tarefa -> orderMenorPrioridade.equals(tarefa.getOrder()))
            .findFirst();
        logger.debug("3");
        Optional<Tarefa> tarefaADiminuirOptional = tarefasUsuario.stream()
            .filter(tarefa -> order.equals(tarefa.getOrder()))
            .findFirst();

        logger.debug("4");
        Tarefa tarefaAAumentar = tarefaAAumentarOptional.get();
        Tarefa tarefaADiminuir = tarefaADiminuirOptional.get();
        
        logger.debug("5");
        tarefaAAumentar.setOrder(order);
        tarefaADiminuir.setOrder(orderMenorPrioridade); // order de maior prioridade
        
        logger.debug("6");
        tarefaService.editarTarefa(tarefaAAumentar);
        tarefaService.editarTarefa(tarefaADiminuir);
        
        logger.debug("7");
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{idTarefa}")
    public ResponseEntity<Void> deletaTarefa(@PathVariable String idTarefa) {
        tarefaService.deletarTarefa(idTarefa);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/completarTarefa/{idTarefa}")
    public ResponseEntity<Void> completarTarefa(@PathVariable String idTarefa, @RequestBody Boolean completa) {
        tarefaService.completarTarefa(idTarefa, completa);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{idUsuario}")
    public List<Tarefa> listarTarefasPorUsuario(@PathVariable String idUsuario) {
        return tarefaService.listarTarefasPorUsuario(idUsuario);
    }
}
