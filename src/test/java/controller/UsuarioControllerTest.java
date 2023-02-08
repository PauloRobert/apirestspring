package controller;

import estudos.java.api.rest.controller.UsuarioController;
import estudos.java.api.rest.model.UsuarioModel;
import estudos.java.api.rest.repository.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {
    @Mock
    private UsuarioRepository repository;

    @InjectMocks
    private UsuarioController usuarioController;

    @Test
    @DisplayName("CT01 - Consultar um ID")
    void testConsultar() {
        Integer codigo = 1;
        UsuarioModel usuario = new UsuarioModel();
        usuario.setCodigo(codigo);

        when(repository.findById(codigo)).thenReturn(Optional.of(usuario));
        ResponseEntity<UsuarioModel> result = usuarioController.consultar(codigo);

        verify(repository, times(1)).findById(codigo);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(usuario, result.getBody());
    }

    @Test
    void testConsultar_NotFound() {
        Integer codigo = 1;

        when(repository.findById(codigo)).thenReturn(Optional.empty());
        ResponseEntity result = usuarioController.consultar(codigo);

        verify(repository, times(1)).findById(codigo);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void testListaUsuario() {
        List<UsuarioModel> usuarios = new ArrayList<>();
        usuarios.add(new UsuarioModel());
        usuarios.add(new UsuarioModel());

        when(repository.findAll()).thenReturn(usuarios);
        ResponseEntity<List<UsuarioModel>> result = usuarioController.listaUsuario();

        verify(repository, times(1)).findAll();
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(usuarios, result.getBody());
    }

    @Test
    void testSalvar() {
        UsuarioModel usuario = new UsuarioModel();

        when(repository.save(usuario)).thenReturn(usuario);
        UsuarioModel result = usuarioController.salvar(usuario);

        verify(repository, times(1)).save(usuario);
        assertEquals(usuario, result);

    }
}