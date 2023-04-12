package estudos.java.api.rest.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;
import java.util.concurrent.ThreadLocalRandom;


// Anotação para criação automática de getters e setters, construtor padrão e outros métodos úteis do Lombok
@NoArgsConstructor
@Data
// Anotação para informar que esta classe é uma entidade de banco de dados
@Entity
public class UsuarioModel {
    // Anotação para informar que este atributo é uma chave primária e que deve ser gerada automaticamente pelo banco de dados
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo")
    private Integer codigo;

    // Anotação para informar que este atributo é uma coluna obrigatória com tamanho máximo de 50 caracteres
    @Column(nullable = false, length = 50)
    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 50, message = "O nome não pode ter mais de 50 caracteres")
    private String nome;

    // Anotação para informar que este atributo é uma coluna obrigatória com tamanho máximo de 10 caracteres e que não pode ser nulo
    @Column(nullable = false, length = 30)
    @NonNull
    @NotBlank(message = "O login é obrigatório")
    @Size(max = 30, message = "O login não pode ter mais de 30 caracteres")
    private  String login;

    // Anotação para informar que este atributo é uma coluna obrigatória com tamanho máximo de 10 caracteres
    @Column(nullable = false, length = 200)
    @Size(max = 200, message = "A senha não pode ter mais de 30 caracteres")
    private  String senha;

    public void setSenha(String senha) {
        // Gerando o custo da senha aleatoriamente, impedindo que um invasor conheça o padrão de senhas utilizado
        SecureRandom secureRandom = new SecureRandom();
        int randomCusto = 5 + secureRandom.nextInt(5);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(randomCusto);
        this.senha = passwordEncoder.encode(senha);
    }

}