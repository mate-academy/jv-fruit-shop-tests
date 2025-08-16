package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    @Test
    void fruitTransactionCreation_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "apple", 10
        );
        assertEquals(FruitTransaction.Operation.SUPPLY, fruitTransaction.getOperation());
        assertEquals("apple", fruitTransaction.getFruit());
        assertEquals(10, fruitTransaction.getQuantity());
    }

    @Test
    void getOperation_Ok() {
        assertEquals(FruitTransaction.Operation.BALANCE,
                FruitTransaction.Operation.getOperation("b"));
        assertEquals(FruitTransaction.Operation.SUPPLY,
                FruitTransaction.Operation.getOperation("s"));
        assertEquals(FruitTransaction.Operation.PURCHASE,
                FruitTransaction.Operation.getOperation("p"));
        assertEquals(FruitTransaction.Operation.RETURN,
                FruitTransaction.Operation.getOperation("r"));
    }

    @Test
    void getOperation_NotOk() {
        assertThrows(IllegalArgumentException.class, () -> {
            FruitTransaction.Operation.getOperation("a");
        });
    }
}
