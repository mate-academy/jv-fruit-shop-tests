package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class FruitTransactionTest {
    @Test
    void createObjectByCorrectData_OK() {
        String expectedName = "apple";
        int expectedQuantity = 123;
        assertEquals(expectedName,
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "apple", 123).getFruit());
        assertEquals(expectedQuantity,
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "apple", 123).getQuantity());
    }

    @Test
    void createObjectByIncorrectData_NotOk() {
        assertThrows(IllegalArgumentException.class,
                () -> new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "apple", -22),
                "quantity should be more then 0!");
    }
}
