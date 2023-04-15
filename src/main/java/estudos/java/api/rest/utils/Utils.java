package estudos.java.api.rest.utils;

import estudos.java.api.rest.model.UsuarioDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.security.SecureRandom;

public final class Utils {

    // Construtor privado para impedir a instanciação da classe
    private Utils() {}

    // Método estático para gerar uma senha criptografada com BCrypt
    public static String criptografarSenha(String senha) {
        SecureRandom secureRandom = new SecureRandom();
        int randomCusto = 5 + secureRandom.nextInt(5);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(randomCusto);
        return passwordEncoder.encode(senha);
    }

    // Método estático para criar um objeto Validator
    public static Validator getValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        return factory.getValidator();
    }

    // Método estático para validar o objeto usuarioDTO
    public static void validarUsuarioDTO(UsuarioDTO usuarioDTO) {
        Validator validator = getValidator();
        validator.validate(usuarioDTO);
    }}
