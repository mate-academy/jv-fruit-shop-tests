package core.basesyntax.model;

import static core.basesyntax.model.FruitTransaction.getOperation;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class FruitTransactionTest {
    private static FruitTransaction.Operation BALANCE;
    private static FruitTransaction.Operation SUPPLY;
    private static FruitTransaction.Operation PURCHASE;
    private static FruitTransaction.Operation RETURN;

    @Before
    public void setUp() {
        BALANCE = FruitTransaction.Operation.BALANCE;
        SUPPLY = FruitTransaction.Operation.SUPPLY;
        PURCHASE = FruitTransaction.Operation.PURCHASE;
        RETURN = FruitTransaction.Operation.RETURN;
    }

    @Test
    public void fruitTransaction_ValidOperation_Ok() {
        assertEquals(BALANCE, getOperation("b"));
        assertEquals(SUPPLY, getOperation("s"));
        assertEquals(PURCHASE, getOperation("p"));
        assertEquals(RETURN, getOperation("r"));
    }

    @Test (expected = RuntimeException.class)
    public void fruitTransaction_InvalidOperation_NotOk() {
        assertEquals(BALANCE, getOperation("v"));
    }
}
