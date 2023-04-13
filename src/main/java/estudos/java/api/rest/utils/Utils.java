package estudos.java.api.rest.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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

    // Outros métodos estáticos úteis podem ser adicionados aqui
}
