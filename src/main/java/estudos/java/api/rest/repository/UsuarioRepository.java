package estudos.java.api.rest.repository;

import estudos.java.api.rest.model.UsuarioModel;
import org.springframework.data.repository.CrudRepository;
public interface UsuarioRepository extends CrudRepository<UsuarioModel, Integer> {
}
