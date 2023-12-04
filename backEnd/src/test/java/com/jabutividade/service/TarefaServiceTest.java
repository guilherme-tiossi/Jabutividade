package com.jabutividade.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;

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
        Tarefa tarefa = new Tarefa("1", "descricao", "usuario1", false);

        when(tarefaRepository.save(any(Tarefa.class))).thenReturn(tarefa);

        Tarefa resultadoCriacao = tarefaService.criarTarefa(tarefa);

        assertEquals(tarefa, resultadoCriacao);

        verify(tarefaRepository, times(1)).save(any(Tarefa.class));

        assertEquals("descricao", resultadoCriacao.getDescricaoTarefa());
        assertFalse(resultadoCriacao.getCompleta());
        assertEquals("usuario1", resultadoCriacao.getIdUsuario());
    }

    @Test
    void testeDeletarTarefa() {
        Tarefa tarefa = new Tarefa("1", "descricao", "usuario1", false);

        Mockito.when(tarefaRepository.save(any(Tarefa.class))).thenReturn(tarefa);

        Tarefa resultadoCriacao = tarefaService.criarTarefa(tarefa);

        assert resultadoCriacao != null;
        assert resultadoCriacao.getIdTarefa().equals("1");

        tarefaService.deletarTarefa(resultadoCriacao.getIdTarefa());

        verify(tarefaRepository, times(1)).deleteById("1");
    }

    @Test
    void testeListarTarefas() {
        Tarefa tarefa = new Tarefa("1", "descricao", "usuario1", false);
        Tarefa tarefa2 = new Tarefa("2", "descricao2", "usuario1", true);

        Mockito.when(tarefaRepository.save(any(Tarefa.class))).thenReturn(tarefa).thenReturn(tarefa2);
        Mockito.when(tarefaRepository.findByIdUsuarioOrderByCompleta("usuario1"))
                .thenReturn(Arrays.asList(tarefa, tarefa2));

        Tarefa resultadoCriacao = tarefaService.criarTarefa(tarefa);
        Tarefa resultadoCriacao2 = tarefaService.criarTarefa(tarefa2);

        assert resultadoCriacao != null;
        assert resultadoCriacao.getIdTarefa().equals("1");
        assert resultadoCriacao2 != null;
        assert resultadoCriacao2.getIdTarefa().equals("2");

        List<Tarefa> tarefasDoUsuario = tarefaService.listarTarefasPorUsuario("usuario1");

        assertNotNull(tarefasDoUsuario);
        assertEquals(2, tarefasDoUsuario.size());
        assertTrue(tarefasDoUsuario.contains(tarefa));
        assertTrue(tarefasDoUsuario.contains(tarefa2));

        verify(tarefaRepository, times(1)).findByIdUsuarioOrderByCompleta("usuario1");
        verify(tarefaRepository, times(2)).save(any(Tarefa.class));
    }

    @Test
    void testeCompletarTarefa() {
        Tarefa tarefa = new Tarefa("1", "descricao", "usuario1", false);

        when(tarefaRepository.findByIdTarefa("1")).thenReturn(Arrays.asList(tarefa));

        tarefaService.completarTarefa("1", true);

        verify(tarefaRepository, times(1)).findByIdTarefa("1");
        verify(tarefaRepository, times(1)).save(any(Tarefa.class));

        Tarefa tarefaAtualizada = tarefaRepository.findByIdTarefa("1").get(0);

        assertEquals(tarefaAtualizada.getCompleta(), true);
    }

    @Test
    void testeEditarTarefa() {
        Tarefa tarefa = new Tarefa("1", "descricao", "usuario1", false);
        Tarefa tarefaAlterar = new Tarefa("1", "descricao2", "usuario1", false);

        when(tarefaRepository.findByIdTarefa("1")).thenReturn(Arrays.asList(tarefa));

        tarefaService.editarTarefa(tarefaAlterar);

        verify(tarefaRepository, times(1)).findByIdTarefa("1");
        verify(tarefaRepository, times(1)).save(any(Tarefa.class));

        Tarefa tarefaAtualizada = tarefaRepository.findByIdTarefa("1").get(0);

        assertEquals(tarefaAtualizada.getDescricaoTarefa(), "descricao2");
    }

}
