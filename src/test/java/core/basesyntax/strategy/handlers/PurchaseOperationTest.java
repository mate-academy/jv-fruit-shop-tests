package core.basesyntax.strategy.handlers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

    @Before
    public void before() {
        Storage.fruits.clear();
        Storage.fruits.put(REGULAR_NAME, START_COUNT);
    }

    @Test
    public void purchaseAny_ok() {
        int expected = START_COUNT / 2;
        PurchaseOperation operation = new PurchaseOperation();
        operation.handle(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                REGULAR_NAME, PURCHASE_COUNT));
        int actual = Storage.fruits.get(REGULAR_NAME);
        assertEquals(actual + " waiting for, but " + expected + " was expected!",
                expected, actual);
    }

    @Test
    public void purchaseAllWork_ok() {
        int expected = 0;
        PurchaseOperation operation = new PurchaseOperation();
        operation.handle(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                REGULAR_NAME, START_COUNT));
        int actual = Storage.fruits.get(REGULAR_NAME);
        assertEquals(actual + " waiting for, but " + expected + " was expected!",
                expected, actual);
    }

    @Test
    public void purchaseMoreThanExist_notOk() {
        PurchaseOperation operation = new PurchaseOperation();
        boolean thrown = false;
        try {
            operation.handle(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                    REGULAR_NAME, START_COUNT + PURCHASE_COUNT));
        } catch (FruitTransactionException e) {
            thrown = true;
        }
        assertTrue("FruitTransactionException expected true"
                + ", but false was expected", thrown);
    }

    @Test
    public void purchaseWhenNotExist_notOk() {
        PurchaseOperation operation = new PurchaseOperation();
        boolean thrown = false;
        try {
            operation.handle(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                    WRONG_NAME, PURCHASE_COUNT));
        } catch (FruitTransactionException e) {
            thrown = true;
        }
        assertTrue("FruitTransactionException expected true"
                + ", but false was expected", thrown);
    }

    @Test
    public void purchaseWhenNull_notOk() {
        PurchaseOperation operation = new PurchaseOperation();
        boolean thrown = false;
        try {
            operation.handle(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                    null, PURCHASE_COUNT));
        } catch (FruitTransactionException e) {
            thrown = true;
        }
        assertTrue("FruitTransactionException expected true"
                + ", but false was expected", thrown);
    }
}
