package tiossi.jabutividade.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tiossi.jabutividade.model.Tarefa;
import java.util.List;
import tiossi.jabutividade.service.TarefaService;

@RestController
@RequestMapping("/apiTarefa")
public class TarefaAPIController {
    
    @Autowired
    private TarefaService tarefaService;

    @PostMapping
    public Tarefa criarTarefa(@RequestBody Tarefa tarefa) {
        return tarefaService.criarTarefa(tarefa);
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
