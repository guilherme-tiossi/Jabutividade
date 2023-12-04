package com.jabutividade.backEnd.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jabutividade.backEnd.entities.Tarefa;
import com.jabutividade.backEnd.repository.TarefaRepository;

@Service
public class TarefaService {
    @Autowired
    private TarefaRepository tarefaRepository;

    public Tarefa criarTarefa(Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }

    public Tarefa editarTarefa(Tarefa tarefa) {
        Tarefa tarefaExistente = tarefaRepository.findByIdTarefa(tarefa.getIdTarefa()).get(0);

        if (tarefaExistente != null) {
            tarefaExistente.setDescricaoTarefa(tarefa.getDescricaoTarefa());
            tarefaExistente.setCompleta(tarefa.getCompleta());
            tarefaExistente.setIdUsuario(tarefa.getIdUsuario());
            
            return tarefaRepository.save(tarefaExistente);
        }
        return tarefaRepository.save(tarefa);
    }

    public void deletarTarefa(String idTarefa) {
        tarefaRepository.deleteById(idTarefa);
    }

    public void completarTarefa(String idTarefa, Boolean completa) {
        List<Tarefa> tarefas = tarefaRepository.findByIdTarefa(idTarefa);

        if (!tarefas.isEmpty()) {
            Tarefa tarefa = tarefas.get(0);
            tarefa.setCompleta(completa);
            tarefaRepository.save(tarefa);
        }
    }

    public List<Tarefa> listarTarefasPorUsuario(String idUsuario) {
        return tarefaRepository.findByIdUsuarioOrderByCompleta(idUsuario);
    }
}
