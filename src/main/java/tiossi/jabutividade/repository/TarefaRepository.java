package tiossi.jabutividade.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import tiossi.jabutividade.model.Tarefa;

public interface TarefaRepository extends MongoRepository<Tarefa, String> {

}
