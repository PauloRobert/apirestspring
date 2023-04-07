package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import estudos.java.api.rest.model.UsuarioModel;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UsuarioModelTest {

    @RepeatedTest(10000)
    public void testGettersAndSetters() {
        UsuarioModel usuario = new UsuarioModel();
        int codigo = (int) (Math.random() * 1000);
        String nome = "Test User " + codigo;
        String login = "testuser" + codigo;
        String senha = "password" + codigo;

        usuario.setCodigo(codigo);
        usuario.setNome(nome);
        usuario.setLogin(login);
        usuario.setSenha(senha);

        assertEquals(codigo, usuario.getCodigo().intValue());
        assertEquals(nome, usuario.getNome());
        assertEquals(login, usuario.getLogin());
        assertEquals(senha, usuario.getSenha());
        System.out.println("Teste numero: " + codigo);
    }

    @Test
    public void testNoArgsConstructor() {
        UsuarioModel usuario = new UsuarioModel();

        assertNotNull(usuario);
    }

}
