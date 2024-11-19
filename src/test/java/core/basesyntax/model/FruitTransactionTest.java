package core.basesyntax.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FruitTransactionTest {
    @Test
    void createObjectByCorrectData_OK() {
        String expectedName = "apple";
        int expectedQuantity = 123;
        Assertions.assertEquals(expectedName,
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "apple", 123).getFruit());
        Assertions.assertEquals(expectedQuantity,
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "apple", 123).getQuantity());
    }

    @Test
    void createObjectByIncorrectData_NotOk() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "apple", -22));
    }
}
