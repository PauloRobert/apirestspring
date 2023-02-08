package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import estudos.java.api.rest.model.UsuarioModel;
import org.junit.jupiter.api.Test;

public class UsuarioModelTest {

    @Test
    public void testGettersAndSetters() {
        UsuarioModel usuario = new UsuarioModel();
        usuario.setCodigo(1);
        usuario.setNome("Test User");
        usuario.setLogin("testuser");
        usuario.setSenha("password");

        assertEquals(1, usuario.getCodigo().intValue());
        assertEquals("Test User", usuario.getNome());
        assertEquals("testuser", usuario.getLogin());
        assertEquals("password", usuario.getSenha());
    }

    @Test
    public void testNoArgsConstructor() {
        UsuarioModel usuario = new UsuarioModel();

        assertNotNull(usuario);
    }

}
