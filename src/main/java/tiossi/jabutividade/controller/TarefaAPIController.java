package tiossi.jabutividade.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tiossi.jabutividade.model.Tarefa;
import tiossi.jabutividade.service.TarefaService;

@RestController
@RequestMapping("/tarefas")
public class TarefaAPIController {
    
    @Autowired
    private TarefaService tarefaService;

    @PostMapping
    public Tarefa criarTarefa(@RequestBody Tarefa tarefa) {
        return tarefaService.criarTarefa(tarefa);
    }

    @DeleteMapping("/{idTarefa}")
    public ResponseEntity<Void> deletaTarefa(@PathVariable String idTarefa) {
        tarefaService.deletaTarefa(idTarefa);
        return ResponseEntity.ok().build();
    }
}
