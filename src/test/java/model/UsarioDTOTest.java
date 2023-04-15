package model;

import estudos.java.api.rest.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;


import static org.junit.jupiter.api.Assertions.assertAll;


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
    public void testGetSetCodigo() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setCodigo(1);
        assertEquals(1, usuarioDTO.getCodigo());
    }

    @Test
    public void testGetSetNome() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("Nome");
        assertEquals("Nome", usuarioDTO.getNome());
    }

    @Test
    public void testGetSetLogin() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setLogin("Login");
        assertEquals("Login", usuarioDTO.getLogin());
    }

    @Test
    public void testGetSetSenha() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setSenha("Senha");
        assertEquals("Senha", usuarioDTO.getSenha());
    }

    @Test
    public void testGetSetDataHoraCadastro() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        LocalDateTime dataHoraCadastro = LocalDateTime.now();
        usuarioDTO.setDataHoraCadastro(dataHoraCadastro);
        assertEquals(dataHoraCadastro, usuarioDTO.getDataHoraCadastro());
    }

    @Test
    public void testAllArgsConstructor() {
        UsuarioDTO usuarioDTO = new UsuarioDTO(1, "Nome", "Login", "Senha", LocalDateTime.now());
        assertNotNull(usuarioDTO);
    }

    @Test
    public void testNoArgsConstructor() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        assertNotNull(usuarioDTO);
    }

    @Test
    public void testToString() {
        UsuarioDTO usuarioDTO = new UsuarioDTO(1, "Nome", "Login", "Senha", LocalDateTime.now());
        String expected = "UsuarioDTO(codigo=1, nome=Nome, login=Login, senha=Senha, dataHoraCadastro=" + usuarioDTO.getDataHoraCadastro() + ")";
        assertEquals(expected, usuarioDTO.toString());
    }

}
