package model;

import estudos.java.api.rest.model.*;
import estudos.java.api.rest.utils.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
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
    void deveValidarCamposObrigatorios() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();

        Exception exception = Assertions.assertThrows(
                javax.validation.ConstraintViolationException.class,
                () -> {
                    Utils.getValidator().validate(usuarioDTO);
                }
        );

        String expectedMessage = "O nome é obrigatório";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));

        usuarioDTO.setNome("João");

        exception = Assertions.assertThrows(
                javax.validation.ConstraintViolationException.class,
                () -> {
                    Utils.getValidator().validate(usuarioDTO);
                }
        );

        expectedMessage = "O login é obrigatório";
        actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));

        usuarioDTO.setLogin("joao123");

        exception = Assertions.assertThrows(
                javax.validation.ConstraintViolationException.class,
                () -> {
                    Utils.getValidator().validate(usuarioDTO);
                }
        );

        expectedMessage = "A senha é obrigatória";
        actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }
}
