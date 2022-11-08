package core.basesyntax.model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    public void incorrectOInputData_NotOk() {
        assertThrows(RuntimeException.class, () -> Operation.getOperation("t"));
    }

    @Test
    public void incorrectInputData_NNull_NotOk() {
        assertThrows(RuntimeException.class, () -> Operation.getOperation(null));
    }
}
