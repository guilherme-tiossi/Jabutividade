package com.jabutividade.backEnd.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

// import org.slf4j.LoggerFactory;
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
// import ch.qos.logback.classic.Logger;

// import com.fasterxml.jackson.core.type.TypeReference;
import com.jabutividade.backEnd.entities.Tarefa;

@RestController
@RequestMapping("/api")
public class TarefaApiController {

    // private static final Logger logger = (Logger)
    // LoggerFactory.getLogger(TarefaApiController.class);

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

    @PutMapping("/aumentarOrderTarefa/{idUsuario}")
    public Map<String, Object> aumentarOrderTarefa(@PathVariable String idUsuario,
            @RequestBody Map<String, Object> requestBody) {

        Map<String, Object> response = new HashMap<>();

        List<Map<String, Object>> listaTarefasMap = (List<Map<String, Object>>) requestBody.get("listaTarefas");
        List<Tarefa> tarefasUsuario = listaTarefasMap.stream()
                .map(tarefaMap -> mapToTarefa(tarefaMap))
                .collect(Collectors.toList());

        Integer order = (Integer) requestBody.get("order");
        Integer orderMaiorPrioridade = order - 1;

        Optional<Tarefa> tarefaAAumentarOptional = tarefasUsuario.stream()
                .filter(tarefa -> order.equals(tarefa.getOrder()))
                .findFirst();
        Optional<Tarefa> tarefaADiminuirOptional = tarefasUsuario.stream()
                .filter(tarefa -> orderMaiorPrioridade.equals(tarefa.getOrder()))
                .findFirst();

        Tarefa tarefaAAumentar = tarefaAAumentarOptional.get();
        Tarefa tarefaADiminuir = tarefaADiminuirOptional.get();

        tarefaAAumentar.setOrder(orderMaiorPrioridade);
        tarefaADiminuir.setOrder(order); // order de menor prioridade

        Boolean sucessoAumentada = (Boolean) tarefaService.editarTarefa(tarefaAAumentar).get("success");
        Boolean sucessoDiminuida = (Boolean) tarefaService.editarTarefa(tarefaADiminuir).get("success");

        if (sucessoAumentada && sucessoDiminuida) {
            response.put("success", true);
        } else {
            response.put("success", false);
            response.put("message", "Erro na alteração de tarefa!");
        }

        return response;
    }

    @PutMapping("/abaixarOrderTarefa/{idUsuario}")
    public Map<String, Object> abaixarOrderTarefa(@PathVariable String idUsuario,
            @RequestBody Map<String, Object> requestBody) {

        Map<String, Object> response = new HashMap<>();

        List<Map<String, Object>> listaTarefasMap = (List<Map<String, Object>>) requestBody.get("listaTarefas");
        List<Tarefa> tarefasUsuario = listaTarefasMap.stream()
                .map(tarefaMap -> mapToTarefa(tarefaMap))
                .collect(Collectors.toList());

        Integer order = (Integer) requestBody.get("order");
        Integer orderMenorPrioridade = order + 1;

        Optional<Tarefa> tarefaAAumentarOptional = tarefasUsuario.stream()
                .filter(tarefa -> orderMenorPrioridade.equals(tarefa.getOrder()))
                .findFirst();
        Optional<Tarefa> tarefaADiminuirOptional = tarefasUsuario.stream()
                .filter(tarefa -> order.equals(tarefa.getOrder()))
                .findFirst();

        Tarefa tarefaAAumentar = tarefaAAumentarOptional.get();
        Tarefa tarefaADiminuir = tarefaADiminuirOptional.get();

        tarefaAAumentar.setOrder(order);
        tarefaADiminuir.setOrder(orderMenorPrioridade); // order de maior prioridade

        Boolean sucessoAumentada = (Boolean) tarefaService.editarTarefa(tarefaAAumentar).get("success");
        Boolean sucessoDiminuida = (Boolean) tarefaService.editarTarefa(tarefaADiminuir).get("success");

        if (sucessoAumentada && sucessoDiminuida) {
            response.put("success", true);
        } else {
            response.put("success", false);
            response.put("message", "Erro na alteração de tarefa!");
        }

        return response;
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

    private Tarefa mapToTarefa(Map<String, Object> tarefaMap) {
        Tarefa tarefa = new Tarefa();
        tarefa.setIdTarefa((String) tarefaMap.get("idTarefa"));
        tarefa.setDescricaoTarefa((String) tarefaMap.get("descricaoTarefa"));
        tarefa.setIdUsuario((String) tarefaMap.get("idUsuario"));
        tarefa.setCompleta((Boolean) tarefaMap.get("completa"));
        tarefa.setOrder((Integer) tarefaMap.get("order"));

        return tarefa;
    }
}
