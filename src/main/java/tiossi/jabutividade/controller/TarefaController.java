package tiossi.jabutividade.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import tiossi.jabutividade.model.Tarefa;
import tiossi.jabutividade.service.TarefaService;

@Controller
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @GetMapping("/cadastroTarefa")
    public String exibirPaginaCadastroTarefa(Model model) {
        model.addAttribute("tarefa", new Tarefa());
        return "tarefa";
    }

    @PostMapping("/criarTarefa")
    public String criarTarefa(@ModelAttribute Tarefa tarefa) {
        tarefaService.criarTarefa(tarefa);
        return "redirect:/cadastroTarefa";
    }
}
