package tiossi.jabutividade.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import tiossi.jabutividade.model.Tarefa;
import tiossi.jabutividade.repository.TarefaRepository;

@SpringBootTest
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

}
