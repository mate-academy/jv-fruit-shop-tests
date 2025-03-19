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
        assertEquals(expectedFruit, fruitTransaction.getFruit());
        FruitTransaction.Operation operation = FruitTransaction.Operation.BALANCE;
        assertEquals(operation, fruitTransaction.getOperation());
        int expectedAmount = 50;
        assertEquals(expectedAmount,fruitTransaction.getQuantity());
        String code = operation.getCode();
        String expectedCode = "b";
        assertEquals(expectedCode,code);
    }

    @Test
    void setters_Ok() {
        fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.fromCode("b"),
                "banana",
                50);
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(50);
        fruitTransaction.setOperation(FruitTransaction.Operation.RETURN);
        assertEquals("apple", fruitTransaction.getFruit());
        assertEquals(50, fruitTransaction.getQuantity());
        assertEquals(FruitTransaction.Operation.RETURN, fruitTransaction.getOperation());
    }
}
