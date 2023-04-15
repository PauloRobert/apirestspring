package estudos.java.api.rest.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class UsuarioDTO {

        private Integer codigo;

        @NotBlank(message = "O nome é obrigatório")
        @Size(max = 50, message = "O nome não pode ter mais de 50 caracteres")
        private String nome;

        @NotBlank(message = "O login é obrigatório")
        @Size(max = 30, message = "O login não pode ter mais de 30 caracteres")
        private String login;

        @NotNull(message = "A senha é obrigatória")
        @Size(max = 200, message = "A senha não pode ter mais de 200 caracteres")
        private String senha;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime dataHoraCadastro;
} // NOSONAR