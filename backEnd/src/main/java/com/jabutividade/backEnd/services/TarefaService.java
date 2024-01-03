package com.jabutividade.backEnd.services;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ch.qos.logback.classic.Logger;
import com.jabutividade.backEnd.entities.Tarefa;
import com.jabutividade.backEnd.repository.TarefaRepository;

@Service
public class TarefaService {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(TarefaService.class);

    @Autowired
    private TarefaRepository tarefaRepository;

    public Tarefa criarTarefa(Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }

    public Integer getOrderTarefa(String idUsuario) {
        List<Tarefa> tarefasUsuario = listarTarefasPorUsuario(idUsuario);
        if (tarefasUsuario != null && !tarefasUsuario.isEmpty()) {
            Collections.sort(tarefasUsuario, Comparator.comparingInt(Tarefa::getOrder));
            return tarefasUsuario.get(tarefasUsuario.size() - 1).getOrder();
        } else {
            return 0;
        }
    }

    public Tarefa editarTarefa(Tarefa tarefa) {
        Tarefa tarefaExistente = tarefaRepository.findByIdTarefa(tarefa.getIdTarefa()).get(0);

        if (tarefaExistente != null) {
            tarefaExistente.setDescricaoTarefa(tarefa.getDescricaoTarefa());
            tarefaExistente.setCompleta(tarefa.getCompleta());
            tarefaExistente.setIdUsuario(tarefa.getIdUsuario());
            tarefaExistente.setOrder(tarefa.getOrder());
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
