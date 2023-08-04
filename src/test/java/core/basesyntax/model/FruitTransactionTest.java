package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private static final FruitTransaction fruitTransaction =
            new FruitTransaction(Operation.BALANCE, "berry", 1);

    @Test
    void getOperation() {
        assertEquals(Operation.BALANCE, fruitTransaction.getOperation());
    }

    @Test
    void getFruit() {
        assertEquals("berry", fruitTransaction.getFruit());
    }

    @Test
    void getQuantity() {
        assertEquals(1, fruitTransaction.getQuantity());
    }
}
