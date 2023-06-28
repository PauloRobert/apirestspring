package estudos.java.api.rest.controller;

import estudos.java.api.rest.model.UsuarioDTO;
import estudos.java.api.rest.model.UsuarioModel;
import estudos.java.api.rest.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @PostMapping(path = "/cadastrar")
    public ResponseEntity<String> salvar(@Valid @RequestBody UsuarioModel usuario) {
        if (repository.existsByLogin(usuario.getLogin())) {
            return ResponseEntity.badRequest().body("Já existe um usuário com este login");
        }

        usuario.setDataHoraCadastro(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        repository.save(usuario);

        return ResponseEntity.ok().body("Usuário cadastrado com sucesso!");
    }

    @GetMapping(path = "/{codigo}")
    public ResponseEntity<UsuarioModel> consultar(@PathVariable("codigo") Integer codigo) {
        Optional<UsuarioModel> usuarioOpt = repository.findById(codigo);
        return usuarioOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/listar")
    public ResponseEntity<List<UsuarioDTO>> listar() {
        List<UsuarioModel> usuarios = repository.findAll();
        List<UsuarioDTO> usuariosDTO = usuarios.stream()
                .map(usuario -> {
                    UsuarioDTO usuarioDTO = new UsuarioDTO();
                    BeanUtils.copyProperties(usuario, usuarioDTO);
                    return usuarioDTO;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(usuariosDTO);
    }

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

    @PutMapping(path = "/atualizar/{codigo}")
    public ResponseEntity<String> atualizar(@PathVariable("codigo") Integer codigo, @Valid @RequestBody UsuarioDTO usuarioDTO) {
        Optional<UsuarioModel> usuarioOpt = repository.findById(codigo);
        if (usuarioOpt.isPresent()) {
            UsuarioModel usuarioAtualizado = usuarioOpt.get();
            BeanUtils.copyProperties(usuarioDTO, usuarioAtualizado, "codigo");
            usuarioAtualizado.setDataHoraCadastro(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
            repository.save(usuarioAtualizado);
            return ResponseEntity.ok("Usuário atualizado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado, já deletado ou nunca cadastrado!");
        }
    }

}
