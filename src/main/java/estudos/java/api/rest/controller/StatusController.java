package estudos.java.api.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {

    /**
     * Verifica o status da API.
     * @return Retorna uma String com a mensagem de status da API.
     */
    @GetMapping(path = "/index")
    public String check(){
        return "index";
    }
} // NOSONAR