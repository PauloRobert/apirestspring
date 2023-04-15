package model;

import estudos.java.api.rest.model.UsuarioModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioModelTest {

    @RepeatedTest(2)
    public void testGettersAndSetters() {
        UsuarioModel usuario = new UsuarioModel();
        int codigo = (int) (Math.random() * 1000);
        String nome = "Test User " + codigo;
        String login = "testuser" + codigo;
        String senha = "password" + codigo;

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(5);
        String hashSenha = encoder.encode(senha);

        usuario.setCodigo(codigo);
        usuario.setNome(nome);
        usuario.setLogin(login);
        usuario.setSenha(hashSenha);

        assertEquals(codigo, usuario.getCodigo().intValue());
        assertEquals(nome, usuario.getNome());
        assertEquals(login, usuario.getLogin());
    }

    @Test
    public void testNoArgsConstructor() {
        UsuarioModel usuario = new UsuarioModel();

        assertNotNull(usuario);
    }

    @Test
    @DisplayName("Testa a criptografia de senha no método setSenha")
    public void testSetSenha() {
        UsuarioModel usuario = new UsuarioModel();
        String senha = "minha_senha";

        usuario.setSenha(senha);

        assertNotEquals(senha, usuario.getSenha());
    }

    @Test
    @DisplayName("Testa a definição da data e hora de cadastro no método setDataHoraCadastro")
    public void testSetDataHoraCadastro() {
        UsuarioModel usuario = new UsuarioModel();
        LocalDateTime dataHoraCadastro = LocalDateTime.now();

        usuario.setDataHoraCadastro(dataHoraCadastro);

        assertEquals(dataHoraCadastro, usuario.getDataHoraCadastro());
    }

}
