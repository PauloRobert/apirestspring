package estudos.java.api.rest.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

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
    private String nome;

    // Anotação para informar que este atributo é uma coluna obrigatória com tamanho máximo de 10 caracteres e que não pode ser nulo
    @Column(nullable = false, length = 10)
    @NonNull
    private  String login;

    // Anotação para informar que este atributo é uma coluna obrigatória com tamanho máximo de 10 caracteres
    @Column(nullable = false, length = 10)
    private  String senha;
}