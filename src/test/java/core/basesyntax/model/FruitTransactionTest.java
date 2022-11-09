package core.basesyntax.model;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction.Operation;
import org.junit.Before;
import org.junit.Test;

public class FruitTransactionTest {
  

    @Test
    public void correctInputData_Ok() {
        assertEquals(BALANCE, Operation.getOperation("b"));
        assertEquals(PURCHASE, Operation.getOperation("p"));
        assertEquals(RETURN, Operation.getOperation("r"));
        assertEquals(SUPPLY, Operation.getOperation("s"));
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
