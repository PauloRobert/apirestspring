package estudos.java.api.rest.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {

    private final ResourceLoader resourceLoader;

    public StatusController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * Verifica o status da API.
     * @return Retorna uma ResponseEntity com a mensagem de status da API.
     */
    @GetMapping(path = "/index")
    public ResponseEntity<String> check() throws IOException {
        File file = resourceLoader.getResource("classpath:/templates/index.html").getFile();
        if (file.exists()) {
            return ResponseEntity.ok("API está funcionando corretamente");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Arquivo index.html não encontrado");
        }
    }
}
