package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private static FruitTransaction fruitTransaction;

    @Test
    void false_Operation_NotOk() {
        String code = "m";
        assertThrows(IllegalArgumentException.class, () -> {
            FruitTransaction.Operation.fromCode(code);
        });
    }

    @Test
    void getters_Ok() {
        fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.fromCode("b"),
                "banana",
                50);
        String expectedFruit = "banana";
        FruitTransaction.Operation operation = FruitTransaction.Operation.BALANCE;
        int expectedAmount = 50;

        assertEquals(expectedFruit, fruitTransaction.getFruit());
        assertEquals(operation, fruitTransaction.getOperation());
        assertEquals(expectedAmount,fruitTransaction.getQuantity());
    }

    @Test
    void setters_Ok() {
        fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.fromCode("b"),
                "banana",
                50);
        fruitTransaction.setFruit("apple");
        assertEquals("apple", fruitTransaction.getFruit());
    }
}
