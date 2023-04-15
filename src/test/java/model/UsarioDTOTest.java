package model;

import estudos.java.api.rest.model.*;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

public class UsarioDTOTest {

    @Test
    void deveCriarUsuarioDTO() {
        Integer codigo = 1;
        String nome = "João";
        String login = "joao123";
        String senha = "123456";
        LocalDateTime dataHoraCadastro = LocalDateTime.now();

        UsuarioDTO usuarioDTO = new UsuarioDTO(codigo, nome, login, senha, dataHoraCadastro);

        assertAll("usuarioDTO",
                () -> Assertions.assertEquals(codigo, usuarioDTO.getCodigo()),
                () -> Assertions.assertEquals(nome, usuarioDTO.getNome()),
                () -> Assertions.assertEquals(login, usuarioDTO.getLogin()),
                () -> Assertions.assertEquals(senha, usuarioDTO.getSenha()),
                () -> Assertions.assertEquals(dataHoraCadastro, usuarioDTO.getDataHoraCadastro())
        );
    }

    @Test
    public void testNomeSize() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("Nome com mais de 50 caracteres....................................");
        usuarioDTO.setLogin("login");
        usuarioDTO.setSenha("senha");

        assertThrows(ConstraintViolationException.class, () -> usuarioDTO.validate());
        assertEquals("O nome não pode ter mais de 50 caracteres", usuarioDTO.getConstraintViolations().iterator().next().getMessage());
    }

    @Test
    public void testLoginSize() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("nome");
        usuarioDTO.setLogin("Login com mais de 30 caracteres.................................");
        usuarioDTO.setSenha("senha");

        assertThrows(ConstraintViolationException.class, () -> usuarioDTO.validate());
        assertEquals("O login não pode ter mais de 30 caracteres", usuarioDTO.getConstraintViolations().iterator().next().getMessage());
    }

    @Test
    public void testSenhaSize() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("nome");
        usuarioDTO.setLogin("login");
        usuarioDTO.setSenha("Senha com mais de 200 caracteres.......................................................................................................................................................");

        assertThrows(ConstraintViolationException.class, () -> usuarioDTO.validate());
        assertEquals("A senha não pode ter mais de 200 caracteres", usuarioDTO.getConstraintViolations().iterator().next().getMessage());
    }

}
