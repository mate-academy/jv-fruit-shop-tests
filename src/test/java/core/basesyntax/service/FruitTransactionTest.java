package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FruitTransaction.Operation;
import java.util.NoSuchElementException;
import org.junit.Test;

public class FruitTransactionTest {
    private static final String BALANCE = "b";
    private static final String PURCHASE = "p";
    private static final String RETURN = "r";
    private static final String SUPPLY = "s";

    @Test
    public void getOperation_balance_ok() {
        Operation expected = Operation.BALANCE;
        Operation actual = FruitTransaction.getOperation(BALANCE);
        assertEquals(expected, actual);
    }

    @Test
    public void getOperation_purchase_ok() {
        Operation expected = Operation.PURCHASE;
        Operation actual = FruitTransaction.getOperation(PURCHASE);
        assertEquals(expected, actual);
    }

    @Test
    public void getOperation_return_ok() {
        Operation expected = Operation.RETURN;
        Operation actual = FruitTransaction.getOperation(RETURN);
        assertEquals(expected, actual);
    }

    @Test
    public void getOperation_supply_ok() {
        Operation expected = Operation.SUPPLY;
        Operation actual = FruitTransaction.getOperation(SUPPLY);
        assertEquals(expected, actual);
    }

    @Test(expected = NoSuchElementException.class)
    public void getOperation_emptyString_notOk() {
        Operation expected = Operation.BALANCE;
        Operation actual = FruitTransaction.getOperation("");
        assertEquals(expected, actual);
    }

    @Test(expected = NoSuchElementException.class)
    public void getOperation_nullValue_notOk() {
        Operation expected = Operation.BALANCE;
        Operation actual = FruitTransaction.getOperation(null);
        assertEquals(expected, actual);
    }
}
