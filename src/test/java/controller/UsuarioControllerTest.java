package controller;

import estudos.java.api.rest.controller.UsuarioController;
import estudos.java.api.rest.model.UsuarioDTO;
import estudos.java.api.rest.model.UsuarioModel;
import estudos.java.api.rest.repository.UsuarioRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    @Mock
    private UsuarioRepository repository;

    @InjectMocks
    private UsuarioController usuarioController;

    @Test
    @DisplayName("Consultar Usuario Pelo Código")
    void deveConsultarUsuarioPeloCodigo() {
        // given
        UsuarioModel usuarioModel = new UsuarioModel();
        usuarioModel.setCodigo(1);
        usuarioModel.setNome("João");

        when(repository.findById(anyInt())).thenReturn(Optional.of(usuarioModel));

        // when
        ResponseEntity responseEntity = usuarioController.consultar(1);

        // then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(usuarioModel, responseEntity.getBody());
    }

    @Test
    @DisplayName("Usuário Inesxistente")
    void deveRetornarNotFoundQuandoConsultarUsuarioInexistente() {
        // given
        when(repository.findById(anyInt())).thenReturn(Optional.empty());

        // when
        ResponseEntity responseEntity = usuarioController.consultar(1);

        // then
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Listar usuários cadastrados")
    void deveListarUsuariosCadastrados() {
        // given
        UsuarioModel usuarioModel1 = new UsuarioModel();
        usuarioModel1.setCodigo(1);
        usuarioModel1.setNome("João");

        UsuarioModel usuarioModel2 = new UsuarioModel();
        usuarioModel2.setCodigo(2);
        usuarioModel2.setNome("Maria");

        List<UsuarioModel> usuarios = Arrays.asList(usuarioModel1, usuarioModel2);

        Mockito.when(repository.findAll()).thenReturn(usuarios);

        // when
        ResponseEntity<List<UsuarioDTO>> responseEntity = usuarioController.listar();

        // then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(usuarios.size(), responseEntity.getBody().size());
        assertEquals(usuarios.get(0).getCodigo(), responseEntity.getBody().get(0).getCodigo());
        assertEquals(usuarios.get(0).getNome(), responseEntity.getBody().get(0).getNome());
        assertEquals(usuarios.get(1).getCodigo(), responseEntity.getBody().get(1).getCodigo());
        assertEquals(usuarios.get(1).getNome(), responseEntity.getBody().get(1).getNome());
    }

    @Test
    @DisplayName("Cadastrar usuário com sucesso")
    void deveSalvarUsuarioComSucesso() {
        // given
        UsuarioModel usuarioModel = new UsuarioModel();
        usuarioModel.setNome("João");
        usuarioModel.setLogin("joao123");
        usuarioModel.setSenha("senha123");

        when(repository.existsByLogin(anyString())).thenReturn(false);
        when(repository.save(any())).thenReturn(usuarioModel);

        // when
        ResponseEntity<?> responseEntity = usuarioController.salvar(usuarioModel);

        // then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Usuário cadastrado com sucesso!", responseEntity.getBody());
    }

    @Disabled
    @Test
    @DisplayName("Cadastrar usuário com erro")
    void deveRetornarInternalServerErrorQuandoSalvarUsuarioComErro() {
        // given
        UsuarioModel usuarioModel = new UsuarioModel();
        usuarioModel.setCodigo(1);
        usuarioModel.setNome("João");

        when(repository.save(any())).thenThrow(new RuntimeException());

        // when
        ResponseEntity<String> responseEntity = usuarioController.salvar(usuarioModel);

        // then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Erro ao cadastrar usuário", responseEntity.getBody());
    }

    @RepeatedTest(10)
    @DisplayName("Deletar usuário com sucesso")
    void deveDeletarUsuarioComSucesso() {
        // Configuração do cenário de teste
        int codigoUsuario = new Random().nextInt(1000) + 1;
        List<String> nomesUsuarios = Arrays.asList("João", "Maria", "Pedro", "Ana");
        String nomeUsuario = nomesUsuarios.get(new Random().nextInt(nomesUsuarios.size()));
        UsuarioModel usuarioModel = new UsuarioModel();
        usuarioModel.setCodigo(codigoUsuario);
        usuarioModel.setNome(nomeUsuario);
        when(repository.findById(codigoUsuario)).thenReturn(Optional.of(usuarioModel));
        doNothing().when(repository).delete(usuarioModel);

        // Execução do teste
        ResponseEntity<String> responseEntity = usuarioController.deletar(codigoUsuario);

        // Validação do resultado
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Usuário " + nomeUsuario + " deletado com sucesso!", responseEntity.getBody());
    }

    @Test
    @DisplayName("Usuário não encontrado ao deletar")

    void deveRetornarUsuarioNaoEncontradoAoDeletar() {
        int codigoUsuario = new Random().nextInt(1000) + 1;

        // given
        when(repository.findById(anyInt())).thenReturn(Optional.empty());

        // when
        ResponseEntity<String> responseEntity = usuarioController.deletar(codigoUsuario);

        // then
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Usuário não encontrado, já deletado ou nunca cadastrado!", responseEntity.getBody());
    }

    @Test
    @DisplayName("Parametro de login existente como True")
    void ParametroLoginExistenteTrue() {
        // given
        UsuarioModel usuarioModel = new UsuarioModel();
        usuarioModel.setCodigo(1);
        usuarioModel.setNome("João");
        usuarioModel.setLogin("joao123");
        usuarioModel.setSenha("senha123");

        when(repository.existsByLogin(anyString())).thenReturn(false);
        when(repository.save(any())).thenReturn(usuarioModel);

        // when
        ResponseEntity<String> responseEntity = usuarioController.salvar(usuarioModel);

        // then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Usuário cadastrado com sucesso!", responseEntity.getBody());
    }
    @Test
    @DisplayName("Parametro de login existente como False")
    void ParametroLoginExistenteFalse(){
        // given
        UsuarioModel usuarioModel = new UsuarioModel();
        usuarioModel.setCodigo(1);
        usuarioModel.setNome("João");
        usuarioModel.setLogin("joao123");
        usuarioModel.setSenha("senha123");

        when(repository.existsByLogin(anyString())).thenReturn(true);

        // when
        ResponseEntity<String> responseEntity = usuarioController.salvar(usuarioModel);

        // then
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Já existe um usuário com este login", responseEntity.getBody());
    }


}