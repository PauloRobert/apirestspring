package model;

import estudos.java.api.rest.model.UsuarioModel;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioModelTest {

    @RepeatedTest(10)
    public void testGettersAndSetters() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

        UsuarioModel usuario = new UsuarioModel();
        int codigo = (int) (Math.random() * 1000);
        String nome = "Test User " + codigo;
        String login = "testuser" + codigo;
        String senha = "password" + codigo;

        usuario.setCodigo(codigo);
        usuario.setNome(nome);
        usuario.setLogin(login);
        usuario.setSenha(encoder.encode(senha));

        assertEquals(codigo, usuario.getCodigo().intValue());
        assertEquals(nome, usuario.getNome());
        assertEquals(login, usuario.getLogin());
        assertTrue(encoder.matches(senha, usuario.getSenha()));
    }

    @Test
    public void testNoArgsConstructor() {
        UsuarioModel usuario = new UsuarioModel();

        assertNotNull(usuario);
    }

}
