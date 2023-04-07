package estudos.java.api.rest.controller;

import estudos.java.api.rest.model.UsuarioModel;
import estudos.java.api.rest.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    /**
     * Consulta um usuário pelo código
     *
     * @param codigo o código do usuário a ser consultado
     * @return o usuário encontrado ou uma mensagem de erro
     */
    @GetMapping(path = "/{codigo}")
    public ResponseEntity consultar(@PathVariable("codigo") Integer codigo) {
        Optional<UsuarioModel> usuarioOpt = repository.findById(codigo);
        if (usuarioOpt.isPresent()) {
            return ResponseEntity.ok().body(usuarioOpt.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Lista todos os usuários cadastrados
     *
     * @return a lista de usuários ou uma mensagem de erro
     */
    @GetMapping(path = "/listar")
    public ResponseEntity<List<UsuarioModel>> listar() {
        List<UsuarioModel> usuarios = repository.findAll();
        return ResponseEntity.ok().body(usuarios);
    }

    /**
     * Salva um usuário
     *
     * @param usuario o usuário a ser salvo
     * @return o usuário salvo ou uma mensagem de erro
     */
    @PostMapping(path = "/salvar")
    public ResponseEntity<UsuarioModel> salvar(@RequestBody UsuarioModel usuario) {
        try {
            UsuarioModel usuarioSalvo = repository.save(usuario);
            return ResponseEntity.ok().body(usuarioSalvo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Deleta um usuário pelo código
     *
     * @param codigo o código do usuário a ser deletado
     * @return uma mensagem de sucesso ou de erro
     */
    @DeleteMapping(path = "/{codigo}")
    public ResponseEntity<String> deletar(@PathVariable("codigo") Integer codigo) {
        Optional<UsuarioModel> usuarioOpt = repository.findById(codigo);
        if (usuarioOpt.isPresent()) {
            UsuarioModel usuario = usuarioOpt.get();
            String nomeUsuario = usuario.getNome();
            repository.delete(usuario);
            return ResponseEntity.ok("Usuário " + nomeUsuario + " deletado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado, já deletado ou nunca cadastrado!");
        }
    }

}