package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class FruitTest {

    @Test
    void createObjectByCorrectData_OK() {
        String expectedName = "apple";
        int expectedQuantity = 123;
        assertEquals(expectedName, new Fruit("apple", 123).getName());
        assertEquals(expectedQuantity, new Fruit("apple", 123).getQuantity());
    }

    @Test
    void createObjectByIncorrectData_NotOk() {
        assertThrows(IllegalArgumentException.class,
                () -> new Fruit("apple", -22),
                "quantity should be more then 0!");
    }
}
