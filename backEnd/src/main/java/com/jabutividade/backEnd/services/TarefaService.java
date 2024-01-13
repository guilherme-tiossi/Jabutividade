package com.jabutividade.backEnd.services;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.HashMap;
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

    public Map<String, Object> criarTarefa(Tarefa tarefa) {
        Map<String, Object> response = new HashMap<>();
        Tarefa tarefaSalva = tarefaRepository.save(tarefa);
        if (tarefaSalva.getIdTarefa().length() > 0) {
            response.put("success", true);
            response.put("tarefa", tarefaSalva);
            return response;
        } else {
            response.put("success", false);
            response.put("tarefa", null);
            response.put("message", "Erro na criação de tarefa, tente novamente.");
            return response;
        }
    }

    public Integer getOrderTarefa(String idUsuario) {
        List<Tarefa> tarefasUsuario = (List<Tarefa>) listarTarefasPorUsuario(idUsuario).get("tarefas");
        if (tarefasUsuario != null && !tarefasUsuario.isEmpty()) {
            Collections.sort(tarefasUsuario, Comparator.comparingInt(Tarefa::getOrder));
            return tarefasUsuario.get(tarefasUsuario.size() - 1).getOrder();
        } else {
            return 0;
        }
    }

    public Map<String, Object> editarTarefa(Tarefa tarefa) {
        Map<String, Object> response = new HashMap<>();
        Tarefa tarefaExistente = tarefa.getIdTarefa().isEmpty() ? null
                : tarefaRepository.findByIdTarefa(tarefa.getIdTarefa()).get(0);
    
        Tarefa tarefaAlterada;
    
        if (tarefaExistente != null) {
            tarefaExistente.setDescricaoTarefa(tarefa.getDescricaoTarefa());
            tarefaExistente.setCompleta(tarefa.getCompleta());
            tarefaExistente.setIdUsuario(tarefa.getIdUsuario());
            tarefaExistente.setOrder(tarefa.getOrder());
            tarefaAlterada = tarefaRepository.save(tarefaExistente);
        } else {
            tarefaAlterada = tarefaRepository.save(tarefa);
        }
    
        if (tarefaAlterada != null && tarefaAlterada.getIdTarefa() != null && tarefaAlterada.getIdTarefa().length() > 0) {
            response.put("success", true);
            response.put("tarefa", tarefaAlterada);
        } else {
            response.put("success", false);
            response.put("message", "Erro na alteração de tarefa!");
        }
    
        return response;
    }
    

    public Map<String, Object> deletarTarefa(String idTarefa) {
        Map<String, Object> response = new HashMap<>();

        try {
            tarefaRepository.deleteById(idTarefa);
            response.put("success", true);
            response.put("message", "Tarefa deletada com sucesso");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Erro ao deletar tarefa: " + e.getMessage());
        }

        return response;
    }

    public Map<String, Object> completarTarefa(String idTarefa, Boolean completa) {
        Map<String, Object> response = new HashMap<>();
        List<Tarefa> tarefas = tarefaRepository.findByIdTarefa(idTarefa);

        if (!tarefas.isEmpty()) {
            Tarefa tarefa = tarefas.get(0);
            tarefa.setCompleta(completa);
            tarefaRepository.save(tarefa);
            response.put("success", true);
        } else {
            response.put("success", false);
            response.put("message", "Erro ao completa tarefa.");
        }

        return response;
    }

    public Map<String, Object> listarTarefasPorUsuario(String idUsuario) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Tarefa> listaTarefas = tarefaRepository.findByIdUsuarioOrderByCompleta(idUsuario);
            response.put("success", true);
            response.put("tarefas", listaTarefas);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Erro ao achar tarefas: " + e.getMessage());
        }

        return response;
    }
}
