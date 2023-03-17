package core.basesyntax.strategy.handlers;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.handlers.FruitTransactionException;
import core.basesyntax.model.FruitTransaction;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationTest {
    private static final String REGULAR_NAME = "banana";
    private static final int START_COUNT = 40;
    private static final int PURCHASE_COUNT = 20;
    private static final String WRONG_NAME = "airplane";
    private final PurchaseOperation operation = new PurchaseOperation();

    @Before
    public void before() {
        Storage.fruits.clear();
        Storage.fruits.put(REGULAR_NAME, START_COUNT);
    }

    @Test
    public void handle_existedValue_ok() {
        int expected = START_COUNT / 2;
        operation.handle(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                REGULAR_NAME, PURCHASE_COUNT));
        int actual = Storage.fruits.get(REGULAR_NAME);
        assertEquals(actual + " waiting for, but " + expected + " was expected!",
                expected, actual);
    }

    @Test
    public void handle_allOfExistValue_ok() {
        int expected = 0;
        operation.handle(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                REGULAR_NAME, START_COUNT));
        int actual = Storage.fruits.get(REGULAR_NAME);
        assertEquals(actual + " waiting for, but " + expected + " was expected!",
                expected, actual);
    }

    @Test(expected = FruitTransactionException.class)
    public void handle_moreThanExistValue_notOk() {
        operation.handle(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                REGULAR_NAME, START_COUNT + PURCHASE_COUNT));
    }

    @Test(expected = FruitTransactionException.class)
    public void handle_notExistValue_notOk() {
        operation.handle(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                WRONG_NAME, PURCHASE_COUNT));
    }

    @Test(expected = FruitTransactionException.class)
    public void handle_nullValue_notOk() {
        operation.handle(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                null, PURCHASE_COUNT));
    }
}
