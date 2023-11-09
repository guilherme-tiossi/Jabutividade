package tiossi.jabutividade.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tiossi.jabutividade.model.Tarefa;
import tiossi.jabutividade.service.TarefaService;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {
    
    @Autowired
    private TarefaService tarefaService;

    @GetMapping("/cadastro")
    public String exibirPaginaCadastroTarefa(Model model) {
        model.addAttribute("tarefa", new Tarefa());
        return "tarefa";
    }

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
