package controller;

import estudos.java.api.rest.controller.StatusController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Testes para StatusController")
public class StatusControllerTest {

    @Test
    @DisplayName("Testa o retorno do método check()")
    public void testCheck() {
        StatusController controller = new StatusController();
        String expected = "index";
        String result = controller.check();
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Verificar se o método check retorna a String index")
    public void StringIndex() {
        StatusController controller = new StatusController();
        assertEquals("index", controller.check());
    }
}