package estudos.java.api.rest.controller;

import estudos.java.api.rest.model.UsuarioDTO;
import estudos.java.api.rest.model.UsuarioModel;
import estudos.java.api.rest.repository.UsuarioRepository;
import jakarta.mail.Message;
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
import java.util.Properties;
import java.util.stream.Collectors;
import jakarta.mail.*;
import jakarta.mail.search.SubjectTerm;
import jakarta.mail.internet.MimeMultipart;

import java.util.Properties;

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
            BeanUtils.copyProperties(usuarioDTO, usuarioAtualizado, "codigo", "dataHoraCadastro");
            repository.save(usuarioAtualizado);
            return ResponseEntity.ok("Usuário atualizado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado, já deletado ou nunca cadastrado!");
        }
    }




    @GetMapping(path = "/acessoEmail")
    public ResponseEntity<String> acessoEmail(@RequestParam("tituloEmail") String tituloEmail) {
        try {
            // Configurar as propriedades para acessar o servidor IMAP do Gmail
            Properties props = new Properties();
            props.setProperty("mail.store.protocol", "imaps");
            props.setProperty("mail.imap.ssl.enable", "true");
            props.setProperty("mail.imap.host", "imap.gmail.com");
            props.setProperty("mail.imap.port", "993");

            // Configurar as credenciais de acesso ao Gmail
            String username = "taxcoe@gmail.com";
            String password = "dtt.2015";

            // Estabelecer a conexão com o servidor IMAP do Gmail
            Session session = Session.getInstance(props, null);
            Store store = session.getStore("imaps");
            store.connect("imap.gmail.com", username, password);

            // Acessar a pasta de entrada (inbox)
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            // Procurar pelos emails com o título especificado
            Message[] messages = inbox.search(new SubjectTerm(tituloEmail));

            // Verificar se há algum email encontrado
            if (messages.length > 0) {
                // Ler o primeiro email encontrado
                Message email = messages[0];

                // Aqui você pode fazer o processamento necessário com o email, como obter o conteúdo, remetente, data etc.
                // Exemplo:
                String remetente = email.getFrom()[0].toString();
                Object conteudo = email.getContent();

                // Se o conteúdo for uma instância de MimeMultipart, você pode extrair o texto do email
                if (conteudo instanceof MimeMultipart) {
                    MimeMultipart mimeMultipart = (MimeMultipart) conteudo;
                    conteudo = mimeMultipart.getBodyPart(0).getContent().toString();
                }

                // Retornar a resposta com os detalhes do email
                return ResponseEntity.ok("Email encontrado. Remetente: " + remetente + ", Conteúdo: " + conteudo);
            } else {
                // Caso nenhum email seja encontrado com o título especificado
                return ResponseEntity.ok("Nenhum email encontrado com o título: " + tituloEmail);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao acessar o email do Gmail");
        }
    }


}
