package estudos.java.api.rest.controller;

import estudos.java.api.rest.model.UsuarioModel;
import estudos.java.api.rest.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @GetMapping(path = "/api/usuario/{codigo}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity consultar(@PathVariable("codigo") Integer codigo) {
        return repository.findById(codigo)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/api/usuario/salvar")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioModel salvar(@RequestBody UsuarioModel usuario) {
        return repository.save(usuario);
    }

    @GetMapping(path = "/api/usuario/listar")
    @ResponseBody
    public ResponseEntity<List<UsuarioModel>> listaUsuario(){
        List<UsuarioModel> usuarios = (List<UsuarioModel>) repository.findAll();

        return new ResponseEntity<List<UsuarioModel>>(usuarios, HttpStatus.OK);
    }
}