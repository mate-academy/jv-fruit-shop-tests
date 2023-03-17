package core.basesyntax.strategy.handlers;

import static org.junit.Assert.assertEquals;

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
    private static SupplyOperation operation = new SupplyOperation();

    @Before
    public void before() {
        Storage.fruits.clear();
        Storage.fruits.put(REGULAR_NAME, START_COUNT);
    }

    @Test
    public void handle_existValue_ok() {
        int expected = START_COUNT + SUPPLY_COUNT;
        operation.handle(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                REGULAR_NAME, SUPPLY_COUNT));
        int actual = Storage.fruits.get(REGULAR_NAME);
        assertEquals(actual + " waiting for, but " + expected + " was expected!",
                expected, actual);
    }

    @Test(expected = FruitTransactionException.class)
    public void handle_notExistValue_notOk() {
        operation.handle(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                WRONG_NAME, SUPPLY_COUNT));
    }

    @Test(expected = FruitTransactionException.class)
    public void handle_nullValue_notOk() {
        operation.handle(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                null, SUPPLY_COUNT));
    }
}
