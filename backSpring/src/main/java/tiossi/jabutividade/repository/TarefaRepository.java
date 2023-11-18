package tiossi.jabutividade.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import tiossi.jabutividade.model.Tarefa;
import java.util.List;

public interface TarefaRepository extends MongoRepository<Tarefa, String> {
    List<Tarefa> findByIdUsuario(String idUsuario);
    List<Tarefa> findByIdTarefa(String idTarefa);
}
