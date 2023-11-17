package tiossi.jabutividade.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tiossi.jabutividade.model.Tarefa;
import tiossi.jabutividade.repository.TarefaRepository;

@Service
public class TarefaService {
    @Autowired
    private TarefaRepository tarefaRepository;

    public Tarefa criarTarefa(Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }

    public Tarefa editarTarefa(Tarefa tarefa) {
        Tarefa tarefaExistente = tarefaRepository.findById(tarefa.getIdTarefa()).orElse(null);

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

    public List<Tarefa> listarTarefasPorUsuario(String idUsuario) {
        return tarefaRepository.findByIdUsuario(idUsuario);
    }
}
