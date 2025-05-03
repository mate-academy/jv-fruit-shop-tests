package core.basesyntax.model;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction.Operation;
import org.junit.Test;

public class FruitTransactionTest {

    @Test
    public void correctInputData_Ok() {
        assertEquals(Operation.BALANCE, Operation.getOperation("b"));
        assertEquals(Operation.PURCHASE, Operation.getOperation("p"));
        assertEquals(Operation.RETURN, Operation.getOperation("r"));
        assertEquals(Operation.SUPPLY, Operation.getOperation("s"));
    }

    @Test(expected = RuntimeException.class)
    public void incorrectOInputData_NotOk() {
        Operation.getOperation("t");
    }

    @Test(expected = RuntimeException.class)
    public void incorrectInputData_NNull_NotOk() {
        Operation.getOperation(null);
    }
}
