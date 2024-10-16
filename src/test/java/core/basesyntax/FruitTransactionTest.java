package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    @Test
    public void getSetOperation_Ok() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.BALANCE);

        assertEquals(FruitTransaction.Operation.BALANCE, transaction.getOperation());
    }

    @Test
    public void operationFromCode_Ok() {
        assertEquals(FruitTransaction.Operation.BALANCE,
                FruitTransaction.Operation.operationFromCode("b"));
    }

    @Test
    public void operationFromCode_invalidInput_NotOk() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            FruitTransaction.Operation.operationFromCode("x");
        });
        assertEquals("can't find operation", exception.getMessage());
    }
}
