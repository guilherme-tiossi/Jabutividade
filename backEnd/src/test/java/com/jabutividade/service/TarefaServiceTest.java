package com.jabutividade.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.jabutividade.backEnd.BackEndApplication;
import com.jabutividade.backEnd.entities.Tarefa;
import com.jabutividade.backEnd.repository.TarefaRepository;
import com.jabutividade.backEnd.services.TarefaService;

@SpringBootTest(classes = BackEndApplication.class)
public class TarefaServiceTest {

    @MockBean
    private TarefaRepository tarefaRepository;

    @Autowired
    private TarefaService tarefaService;

    @Test
    void testeCriarTarefa() {
        Tarefa tarefa = new Tarefa("1", "descricao", "usuario1", false, 0);

        when(tarefaRepository.save(any(Tarefa.class))).thenReturn(tarefa);

        Map<String, Object> response = tarefaService.criarTarefa(tarefa);

        Tarefa resultadoCriacao = (Tarefa) response.get("tarefa");

        assertEquals(tarefa, resultadoCriacao);

        verify(tarefaRepository, times(1)).save(any(Tarefa.class));

        assertEquals("descricao", resultadoCriacao.getDescricaoTarefa());
        assertFalse(resultadoCriacao.getCompleta());
        assertEquals("usuario1", resultadoCriacao.getIdUsuario());

        assertTrue((Boolean) response.get("success"));
    }

    @Test
    void testeDeletarTarefa() {
        Tarefa tarefa = new Tarefa("1", "descricao", "usuario1", false, 0);

        Mockito.when(tarefaRepository.save(any(Tarefa.class))).thenReturn(tarefa);

        Tarefa resultadoCriacao = (Tarefa) tarefaService.criarTarefa(tarefa).get("tarefa");

        assert resultadoCriacao != null;
        assert resultadoCriacao.getIdTarefa().equals("1");

        Map<String, Object> response = tarefaService.deletarTarefa(resultadoCriacao.getIdTarefa());

        verify(tarefaRepository, times(1)).deleteById("1");

        assertTrue((Boolean) response.get("success"));
    }

    @Test
    void testeListarTarefas() {
        Tarefa tarefa = new Tarefa("1", "descricao", "usuario1", false, 0);
        Tarefa tarefa2 = new Tarefa("2", "descricao2", "usuario1", true, 0);

        Mockito.when(tarefaRepository.save(any(Tarefa.class))).thenReturn(tarefa).thenReturn(tarefa2);
        Mockito.when(tarefaRepository.findByIdUsuarioOrderByCompleta("usuario1"))
                .thenReturn(Arrays.asList(tarefa, tarefa2));

        Tarefa resultadoCriacao = (Tarefa) tarefaService.criarTarefa(tarefa).get("tarefa");
        Tarefa resultadoCriacao2 = (Tarefa) tarefaService.criarTarefa(tarefa2).get("tarefa");

        assert resultadoCriacao != null;
        assert resultadoCriacao.getIdTarefa().equals("1");
        assert resultadoCriacao2 != null;
        assert resultadoCriacao2.getIdTarefa().equals("2");

        Map<String, Object> response = tarefaService.listarTarefasPorUsuario("usuario1");
        List<Tarefa> tarefasDoUsuario = (List<Tarefa>) response.get("tarefas");

        assertNotNull(tarefasDoUsuario);
        assertEquals(2, tarefasDoUsuario.size());
        assertTrue(tarefasDoUsuario.contains(tarefa));
        assertTrue(tarefasDoUsuario.contains(tarefa2));

        verify(tarefaRepository, times(1)).findByIdUsuarioOrderByCompleta("usuario1");
        verify(tarefaRepository, times(2)).save(any(Tarefa.class));

        assertTrue((Boolean) response.get("success"));
    }

    @Test
    void testeCompletarTarefa() {
        Tarefa tarefa = new Tarefa("1", "descricao", "usuario1", false, 0);

        when(tarefaRepository.findByIdTarefa("1")).thenReturn(Arrays.asList(tarefa));

        Map<String, Object> response = tarefaService.completarTarefa("1", true);

        verify(tarefaRepository, times(1)).findByIdTarefa("1");
        verify(tarefaRepository, times(1)).save(any(Tarefa.class));

        Tarefa tarefaAtualizada = tarefaRepository.findByIdTarefa("1").get(0);

        assertEquals(tarefaAtualizada.getCompleta(), true);

        assertTrue((Boolean) response.get("success"));
    }

    @Test
    void testeEditarTarefa() {
        Tarefa tarefa = new Tarefa("1", "descricao", "usuario1", false, 0);
        Tarefa tarefaAlterar = new Tarefa("1", "descricao2", "usuario1", false, 1);
    
        when(tarefaRepository.findByIdTarefa("1")).thenReturn(Arrays.asList(tarefa));
        when(tarefaRepository.save(any(Tarefa.class))).thenReturn(null); 
    
        Map<String, Object> response = tarefaService.editarTarefa(tarefaAlterar);
    
        verify(tarefaRepository, times(1)).findByIdTarefa("1");
        verify(tarefaRepository, times(1)).save(any(Tarefa.class));
    
        assertFalse((Boolean) response.get("success"));
        assertEquals("Erro na alteração de tarefa!", response.get("message"));
    }
    

}
