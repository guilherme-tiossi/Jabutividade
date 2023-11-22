package tiossi.jabutividade.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import tiossi.jabutividade.model.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    List<Usuario> findByIdUsuario(String idUsuario);
    Optional<Usuario> findByEmail(String email);
    Boolean existsByUsuario(String usuario);
    Boolean existsByEmail(String email);
}
