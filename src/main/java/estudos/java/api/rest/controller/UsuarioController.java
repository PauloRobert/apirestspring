package estudos.java.api.rest.controller;

import estudos.java.api.rest.model.UsuarioModel;
import estudos.java.api.rest.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

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

    @GetMapping(path = "/api/usuario/listar")
    @ResponseBody
    public ResponseEntity<List<UsuarioModel>> listaUsuario(){
        List<UsuarioModel> usuarios = (List<UsuarioModel>) repository.findAll();

        return new ResponseEntity<List<UsuarioModel>>(usuarios, HttpStatus.OK);
    }

    @PostMapping(path = "/api/usuario/salvar")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioModel salvar(@RequestBody UsuarioModel usuario) {
        try {
            return repository.save(usuario);
        } catch (Exception msg) {
            System.out.println("Erro interno");
        }
        return usuario;
    }

    @DeleteMapping(path = "/api/usuario/{codigo}")
    public ResponseEntity<String> deletar(@PathVariable("codigo") Integer codigo) {
        Optional<UsuarioModel> usuarioOpt = repository.findById(codigo);
        if (usuarioOpt.isPresent()) {
            UsuarioModel usuario = usuarioOpt.get();
            String nomeUsuario = usuario.getNome();
            repository.delete(usuario);
            return ResponseEntity.ok("Usuário " + nomeUsuario + " deletado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado, já deletado ou nunca cadastrado!");        }
    }

}