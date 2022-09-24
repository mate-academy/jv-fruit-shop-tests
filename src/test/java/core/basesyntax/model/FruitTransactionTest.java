package core.basesyntax.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FruitTransactionTest extends FruitTransaction {
    private Operation BALANCE;
    private Operation SUPPLY;
    private Operation RETURN;
    private Operation PURCHASE;

    @Before
    public void setUp() {
        BALANCE = Operation.BALANCE;
        SUPPLY = Operation.SUPPLY;
        RETURN = Operation.RETURN;
        PURCHASE = Operation.PURCHASE;
    }

    @Test
    public void FruitTransaction_ValidOperations_OK() {
        assertEquals(BALANCE, Operation.getOperation("b"));
        assertEquals(SUPPLY, Operation.getOperation("s"));
        assertEquals(RETURN, Operation.getOperation("r"));
        assertEquals(PURCHASE, Operation.getOperation("p"));
    }

    @Test (expected = RuntimeException.class)
    public void fruitTransaction_InvalidOperation_NotOk() {
        assertEquals(BALANCE, Operation.getOperation(""));
    }
}