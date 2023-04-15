package estudos.java.api.rest.controller;

import estudos.java.api.rest.model.UsuarioModel;
import estudos.java.api.rest.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;



@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioRepository repository;
    /**
     * Salva um usuário
     *
     * @param usuario o usuário a ser salvo
     * @return o usuário salvo ou uma mensagem de erro
     */
    @PostMapping(path = "/cadastrar")
    public ResponseEntity<String> salvar(@Valid @RequestBody UsuarioModel usuario) {
        try {
            if (repository.existsByLogin(usuario.getLogin())) {
                return ResponseEntity.badRequest().body("Já existe um usuário com este login");
            }

            usuario.setDataHoraCadastro(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
            repository.save(usuario);

            return ResponseEntity.ok().body("Usuário cadastrado com sucesso!");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

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
     * Deleta um usuário pelo código
     *
     * @param codigo o código do usuário a ser deletado
     * @return uma mensagem de sucesso ou de erro
     */
    @DeleteMapping(path = "/deletar/{codigo}")
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
    /**
     * Atualiza os dados de um usuário pelo código
     *
     * @param codigo o código do usuário a ser atualizado
     * @param usuario os novos dados do usuário
     * @return uma mensagem de sucesso ou de erro
     */
    @PutMapping(path = "/atualizar/{codigo}")
    public ResponseEntity<String> atualizar(@PathVariable("codigo") Integer codigo, @Valid @RequestBody UsuarioModel usuario) {
        try {
            Optional<UsuarioModel> usuarioOpt = repository.findById(codigo);
            if (usuarioOpt.isPresent()) {
                UsuarioModel usuarioAtualizado = usuarioOpt.get();
                if (usuario.getNome() != null) {
                    usuarioAtualizado.setNome(usuario.getNome());
                }
                if (usuario.getSenha() != null) {
                    usuarioAtualizado.setSenha(usuario.getSenha());
                }
                repository.save(usuarioAtualizado);
                return ResponseEntity.ok("Usuário atualizado com sucesso!");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado, já deletado ou nunca cadastrado!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não foi possível atualizar o usuário: " + e.getMessage());
        }
    }


}