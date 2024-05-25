package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    @Test
    void operation_getOperationByValue_ok() {
        assertEquals(FruitTransaction.Operation.BALANCE,
                FruitTransaction.Operation.getOperationByValue("b"));
        assertEquals(FruitTransaction.Operation.SUPPLY,
                FruitTransaction.Operation.getOperationByValue("s"));
        assertEquals(FruitTransaction.Operation.PURCHASE,
                FruitTransaction.Operation.getOperationByValue("p"));
        assertEquals(FruitTransaction.Operation.RETURN,
                FruitTransaction.Operation.getOperationByValue("r"));
    }

    @Test
    void operation_getOperationByValueInvalid_notOk() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> FruitTransaction.Operation.getOperationByValue("x"));
        assertEquals("Invalid value of Operation x", exception.getMessage());
    }

    @Test
    void operation_getOperation_ok() {
        assertEquals("b", FruitTransaction.Operation.BALANCE.getOperation());
        assertEquals("s", FruitTransaction.Operation.SUPPLY.getOperation());
        assertEquals("p", FruitTransaction.Operation.PURCHASE.getOperation());
        assertEquals("r", FruitTransaction.Operation.RETURN.getOperation());
    }
}
