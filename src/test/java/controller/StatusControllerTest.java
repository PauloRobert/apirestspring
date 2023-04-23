package controller;

import static org.mockito.Mockito.when;
import java.io.File;
import java.io.IOException;

import estudos.java.api.rest.controller.StatusController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class StatusControllerTest {

    @Mock
    private ResourceLoader resourceLoader;

    private StatusController statusController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        statusController = new StatusController(resourceLoader);
    }

    @Test
    void shouldReturnStatusOkWhenIndexHtmlExists() throws IOException {
        Resource resource = Mockito.mock(Resource.class);
        File file = Mockito.mock(File.class);

        when(resourceLoader.getResource("classpath:/templates/index.html")).thenReturn(resource);
        when(resource.getFile()).thenReturn(file);
        when(file.exists()).thenReturn(true);

        ResponseEntity<String> response = statusController.check();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("API está funcionando corretamente", response.getBody());
    }

    @Test
    void shouldReturnStatusInternalServerErrorWhenIndexHtmlDoesNotExist() throws IOException {
        Resource resource = Mockito.mock(Resource.class);
        File file = Mockito.mock(File.class);

        when(resourceLoader.getResource("classpath:/templates/index.html")).thenReturn(resource);
        when(resource.getFile()).thenReturn(file);
        when(file.exists()).thenReturn(false);

        ResponseEntity<String> response = statusController.check();

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Assertions.assertEquals("Arquivo index.html não encontrado", response.getBody());
    }


    @Test
    void shouldReturnStatusInternalServerErrorWhenResourceLoadingFails() throws IOException {
        Resource resource = Mockito.mock(Resource.class);

        when(resourceLoader.getResource("classpath:/templates/index.html")).thenReturn(resource);
        when(resource.getFile()).thenThrow(new IOException("Falha ao carregar arquivo"));

        ResponseEntity<String> response = statusController.check();

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Assertions.assertEquals("Falha ao carregar arquivo", response.getBody());
    }


}
