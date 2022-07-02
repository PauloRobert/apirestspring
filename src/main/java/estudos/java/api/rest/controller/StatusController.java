package estudos.java.api.rest.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class StatusController {

    //Criando o método para checar o status da api
    @GetMapping(path = "/index")
    public String check(){
        return "index";
    }

}

