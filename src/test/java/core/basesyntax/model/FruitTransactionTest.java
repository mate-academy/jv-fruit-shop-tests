package core.basesyntax.model;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction.Operation;
import org.junit.Before;
import org.junit.Test;

public class FruitTransactionTest {
    private static FruitTransaction.Operation BALANCE;
    private static FruitTransaction.Operation PURCHASE;
    private static FruitTransaction.Operation RETURN;
    private static FruitTransaction.Operation SUPPLY;

    @Before
    public void setUp() {
        BALANCE = Operation.BALANCE;
        PURCHASE = Operation.PURCHASE;
        RETURN = Operation.RETURN;
        SUPPLY = Operation.SUPPLY;
    }

    @Test
    public void correctInputData_Ok() {
        assertEquals(BALANCE, Operation.getOperation("b"));
        assertEquals(PURCHASE, Operation.getOperation("p"));
        assertEquals(RETURN, Operation.getOperation("r"));
        assertEquals(SUPPLY, Operation.getOperation("s"));
    }

    @Test(expected = RuntimeException.class)
    public void incorrectOInputData_NotOk() {
        assertEquals(Operation.BALANCE, Operation.getOperation("c"));
    }

    @Test(expected = RuntimeException.class)
    public void incorrectInputData_NNull_NotOk() {
        assertEquals(Operation.BALANCE,Operation.getOperation(null));
    }
}
