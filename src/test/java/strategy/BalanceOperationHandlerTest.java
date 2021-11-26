package strategy;

import static org.junit.Assert.assertEquals;

import db.Storage;
import model.Fruit;
import model.TransactionDto;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler operationHandler;
    private final Fruit fruit = new Fruit("banana");

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new BalanceOperationHandler();
    }

    @Test
    public void balanceOperation_correctWorkOperation_ok() {
        int expected = 100;
        TransactionDto transaction = new TransactionDto("operation", "banana", 100);
        operationHandler.apply(transaction);
        int actual = Storage.storage.get(fruit);
        assertEquals(expected, actual);
    }

    @Test
    public void balanceOperation_addBalanceTwice_notOk() {
        int expected = 100;
        TransactionDto transaction = new TransactionDto("operation", "banana", 100);
        operationHandler.apply(transaction);
        operationHandler.apply(transaction);
        int actual = Storage.storage.get(fruit);
        assertEquals(expected, actual);
    }

    @After
    public void afterEachTest() {
        Storage.storage.clear();
    }
}
