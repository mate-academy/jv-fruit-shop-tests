package core.basesyntax.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FruitDaoTest {

    @Test
    void createObjectByCorrectData_OK() {
        String expectedName = "apple";
        int expectedQuantity = 123;
        Assertions.assertEquals(expectedName, new FruitDao("apple", 123).getName());
        Assertions.assertEquals(expectedQuantity, new FruitDao("apple", 123).getQuantity());
    }

    @Test
    void createObjectByIncorrectData_NotOk() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new FruitDao("apple", -22));
    }
}
