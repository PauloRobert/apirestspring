package repository;

import estudos.java.api.rest.model.UsuarioModel;
import estudos.java.api.rest.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository repository;

    @Test
    void whenFindById_thenReturnUsuarioModel() {
        // Given
        UsuarioModel usuario = new UsuarioModel();
        usuario.setNome("Test User");
        repository.save(usuario);

        // When
        Optional<UsuarioModel> found = repository.findById(usuario.getCodigo());

        // Then
        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getNome()).isEqualTo("Test User");
    }

    @Test
    void whenSave_thenReturnUsuarioModel() {
        // Given
        UsuarioModel usuario = new UsuarioModel();
        usuario.setNome("Test User");

        // When
        UsuarioModel saved = repository.save(usuario);

        // Then
        assertThat(saved).isEqualTo(usuario);
    }

}

