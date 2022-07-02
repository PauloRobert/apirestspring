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

//Classe de entidade de banco de dados
@NoArgsConstructor
@Data
@Entity
public class UsuarioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo")
    private Integer codigo;

    @Column(nullable = false, length = 50)
    private String nome;

    @Column(nullable = false, length = 10)
    @NonNull
    private  String login;

    @Column(nullable = false, length = 10)
    private  String senha;
}
