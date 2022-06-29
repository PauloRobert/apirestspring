package estudos.java.api.rest.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {

    //Criando o método para checar o status da api
    @GetMapping(path = "/api/status")
    public String check(){
        return "online";
    }

}

