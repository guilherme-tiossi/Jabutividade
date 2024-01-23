package com.jabutividade.backEnd.services;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jabutividade.backEnd.entities.Tarefa;
import com.jabutividade.backEnd.repository.TarefaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TarefaService {
    private static final Logger log = LoggerFactory.getLogger(TarefaService.class);
    
    @Autowired
    private TarefaRepository tarefaRepository;

    public Integer getOrderTarefa(String idUsuario) {
        List<Tarefa> tarefasUsuario = (List<Tarefa>) listarTarefasPorUsuario(idUsuario).get("tarefas");
        if (tarefasUsuario != null && !tarefasUsuario.isEmpty()) {
            Collections.sort(tarefasUsuario, Comparator.comparingInt(Tarefa::getOrder));
            return tarefasUsuario.get(tarefasUsuario.size() - 1).getOrder();
        } else {
            return 0;
        }
    }

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

    public Map<String, Object> editarTarefa(Tarefa tarefa) {
        log.info("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------tarefa: {}", tarefa);
        Map<String, Object> response = new HashMap<>();
        Tarefa tarefaExistente = tarefa.getIdTarefa().isEmpty() ? null
                : tarefaRepository.findByIdTarefa(tarefa.getIdTarefa()).get(0);
    
        Tarefa tarefaAlterada = null;
        Boolean success = false;
    
        if (tarefaExistente != null) {
            tarefaExistente.setDescricaoTarefa(tarefa.getDescricaoTarefa());
            tarefaExistente.setCompleta(tarefa.getCompleta());
            tarefaExistente.setIdUsuario(tarefa.getIdUsuario());
            tarefaExistente.setOrder(tarefa.getOrder());
            tarefaAlterada = tarefaRepository.save(tarefaExistente);
            success = true;
        }
    
        if (success) {
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

    public Map<String, Object> priorizarTarefa(Map<String, Object> objeto) {

        Map<String, Object> response = new HashMap<>();

        log.info("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------objeto: {}", objeto);
        List<Map<String, Object>> listaTarefasMap = (List<Map<String, Object>>) objeto.get("listaTarefas");
        
        log.info("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------listaTarefasMap: {}", listaTarefasMap);

        List<Tarefa> tarefasUsuario = listaTarefasMap.stream()
                .map(tarefaMap -> mapToTarefa(tarefaMap))
                .collect(Collectors.toList());
                
        log.info("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------tarefasUsuario: {}", tarefasUsuario);


        Integer order = (Integer) objeto.get("order");
        Integer orderMaiorPrioridade = order - 1;

        Optional<Tarefa> tarefaPriorizarOptional = tarefasUsuario.stream()
                .filter(tarefa -> order.equals(tarefa.getOrder()))
                .findFirst();
        Optional<Tarefa> tarefaPostergarOptional = tarefasUsuario.stream()
                .filter(tarefa -> orderMaiorPrioridade.equals(tarefa.getOrder()))
                .findFirst();
                log.info("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------tarefa a priorizar optional: {}", tarefaPriorizarOptional);

        Tarefa tarefaPriorizar = tarefaPriorizarOptional.get();
        Tarefa tarefaPostergar = tarefaPostergarOptional.get();

        tarefaPriorizar.setOrder(orderMaiorPrioridade);
        tarefaPostergar.setOrder(order); // order de menor prioridade
        log.info("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------tarefa a priorizar: {}", tarefaPriorizar);

        Boolean sucessoAumentada = (Boolean) editarTarefa(tarefaPriorizar).get("success");
        Boolean sucessoDiminuida = (Boolean) editarTarefa(tarefaPostergar).get("success");

        if (sucessoAumentada && sucessoDiminuida) {
            response.put("success", true);
        } else {
            response.put("success", false);
            response.put("message", "Erro na alteração de tarefa!");
        }

        return response;
    }

    public Map<String, Object> postergarTarefa(Map<String, Object> objeto) {
        Map<String, Object> response = new HashMap<>();

        List<Map<String, Object>> listaTarefasMap = (List<Map<String, Object>>) objeto.get("listaTarefas");
        List<Tarefa> tarefasUsuario = listaTarefasMap.stream()
                .map(tarefaMap -> mapToTarefa(tarefaMap))
                .collect(Collectors.toList());

        Integer order = (Integer) objeto.get("order");
        Integer orderMenorPrioridade = order + 1;

        Optional<Tarefa> tarefaPriorizarOptional = tarefasUsuario.stream()
                .filter(tarefa -> orderMenorPrioridade.equals(tarefa.getOrder()))
                .findFirst();
        Optional<Tarefa> tarefaPostergarOptional = tarefasUsuario.stream()
                .filter(tarefa -> order.equals(tarefa.getOrder()))
                .findFirst();

        Tarefa tarefaPriorizar = tarefaPriorizarOptional.get();
        Tarefa tarefaPostergar = tarefaPostergarOptional.get();

        tarefaPriorizar.setOrder(order);
        tarefaPostergar.setOrder(orderMenorPrioridade);

        Boolean sucessoPriorizada = (Boolean) editarTarefa(tarefaPriorizar).get("success");
        Boolean sucessoPostergada = (Boolean) editarTarefa(tarefaPostergar).get("success");

        if (sucessoPriorizada && sucessoPostergada) {
            response.put("success", true);
        } else {
            response.put("success", false);
            response.put("message", "Erro na alteração de tarefa!");
        }

        return response;
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
