package core.basesyntax.strategy.handlers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.handlers.FruitTransactionException;
import core.basesyntax.model.FruitTransaction;
import org.junit.Before;
import org.junit.Test;

public class SupplyOperationTest {
    private static final String REGULAR_NAME = "banana";
    private static final int START_COUNT = 40;
    private static final int SUPPLY_COUNT = 10;
    private static final String WRONG_NAME = "airplane";

    @Before
    public void before() {
        Storage.fruits.clear();
        Storage.fruits.put(REGULAR_NAME, START_COUNT);
    }

    @Test
    public void supplyExist_ok() {
        int expected = START_COUNT + SUPPLY_COUNT;
        SupplyOperation operation = new SupplyOperation();
        operation.handle(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                REGULAR_NAME, SUPPLY_COUNT));
        int actual = Storage.fruits.get(REGULAR_NAME);
        assertEquals(actual + " waiting for, but " + expected + " was expected!",
                expected, actual);
    }

    @Test
    public void supplyNotExist_notOk() {
        SupplyOperation operation = new SupplyOperation();
        boolean thrown = false;
        try {
            operation.handle(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                    WRONG_NAME, SUPPLY_COUNT));
        } catch (FruitTransactionException e) {
            thrown = true;
        }
        assertTrue("FruitTransactionException expected true"
                + ", but false was expected", thrown);
    }

    @Test
    public void supplyNull_notOk() {
        SupplyOperation operation = new SupplyOperation();
        boolean thrown = false;
        try {
            operation.handle(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                    null, SUPPLY_COUNT));
        } catch (FruitTransactionException e) {
            thrown = true;
        }
        assertTrue("FruitTransactionException expected true"
                + ", but false was expected", thrown);
    }
}
