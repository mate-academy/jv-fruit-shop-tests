package core.basesyntax.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class FruitTransactionTest extends FruitTransaction {
    private Operation balanceOption;
    private Operation supplyOption;
    private Operation returnOption;
    private Operation purchaseOption;

    @Before
    public void setUp() {
        balanceOption = Operation.BALANCE;
        supplyOption = Operation.SUPPLY;
        returnOption = Operation.RETURN;
        purchaseOption = Operation.PURCHASE;
    }

    @Test
    public void fruitTransaction_ValidOperations_OK() {
        assertEquals(balanceOption, Operation.getOperation("b"));
        assertEquals(supplyOption, Operation.getOperation("s"));
        assertEquals(returnOption, Operation.getOperation("r"));
        assertEquals(purchaseOption, Operation.getOperation("p"));
    }

    @Test (expected = RuntimeException.class)
    public void fruitTransaction_InvalidOperation_NotOk() {
        assertEquals(balanceOption, Operation.getOperation(""));
    }
}
