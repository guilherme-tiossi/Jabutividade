package com.jabutividade.backEnd.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

// import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jabutividade.backEnd.services.TarefaService;
import com.jabutividade.backEnd.entities.Tarefa;

@RestController
@RequestMapping("/api")
public class TarefaApiController {

    @Autowired
    private TarefaService tarefaService;

    @PostMapping
    public Map<String, Object> criarTarefa(@RequestBody Tarefa tarefa) {
        tarefa.setOrder(tarefaService.getOrderTarefa(tarefa.getIdUsuario()) + 1);
        return tarefaService.criarTarefa(tarefa);
    }

    @PutMapping("/editar")
    public Map<String, Object> editarTarefa(@RequestBody Tarefa tarefa) {
        return tarefaService.editarTarefa(tarefa);
    }

    @PutMapping("/priorizarOrderTarefa")
    public Map<String, Object> priorizarOrderTarefa(@RequestBody Map<String, Object> requestBody) {
        return tarefaService.priorizarTarefa(requestBody);
    }

    @PutMapping("/postergarOrderTarefa")
    public Map<String, Object> postergarOrderTarefa(@RequestBody Map<String, Object> requestBody) {
        return tarefaService.postergarTarefa(requestBody);
    }

    @DeleteMapping("/{idTarefa}")
    public Map<String, Object> deletaTarefa(@PathVariable String idTarefa) {
        return tarefaService.deletarTarefa(idTarefa);
    }

    @PutMapping("/completarTarefa/{idTarefa}")
    public Map<String, Object> completarTarefa(@PathVariable String idTarefa, @RequestBody Boolean completa) {
        return tarefaService.completarTarefa(idTarefa, completa);
    }

    @GetMapping("/{idUsuario}")
    public Map<String, Object> listarTarefasPorUsuario(@PathVariable String idUsuario) {
        return tarefaService.listarTarefasPorUsuario(idUsuario);
    }
}
