package core.basesyntax.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    @Test
    void getOperation_ValidCode_Ok() {
        assertEquals(FruitTransaction.Operation.SUPPLY,
                FruitTransaction.Operation.getOperation("s"));
        assertEquals(FruitTransaction.Operation.BALANCE,
                FruitTransaction.Operation.getOperation("b"));
        assertEquals(FruitTransaction.Operation.PURCHASE,
                FruitTransaction.Operation.getOperation("p"));
        assertEquals(FruitTransaction.Operation.RETURN,
                FruitTransaction.Operation.getOperation("r"));
    }

    @Test
    void getOperation_InvalidCode_NotOk() {
        assertThrows(RuntimeException.class,
                () -> FruitTransaction.Operation.getOperation("f"));
    }
}
