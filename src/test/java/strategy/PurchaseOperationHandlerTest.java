package strategy;

import static org.junit.Assert.assertEquals;

import db.Storage;
import model.Fruit;
import model.TransactionDto;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler operationHandler;
    private final Fruit fruit = new Fruit("banana");

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new PurchaseOperationHandler();
    }

    @Test
    public void purchaseOperation_correctWorkOperation_ok() {
        int expected = 80;
        Storage.storage.put(fruit, 100);
        TransactionDto transaction = new TransactionDto("operation", "banana", 20);
        operationHandler.apply(transaction);
        int actual = Storage.storage.get(fruit);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseOperation_purchaseFromEmptyStorage_notOk() {
        TransactionDto transaction = new TransactionDto("operation", "banana", 10);
        operationHandler.apply(transaction);
    }

    @Test
    public void purchaseOperation_purchaseAllThereIs_ok() {
        Storage.storage.put(fruit, 10);
        TransactionDto transaction = new TransactionDto("operation", "banana", 10);
        operationHandler.apply(transaction);
        int actual = Storage.storage.get(fruit);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseOperation_purchaseMuchThanHave_notOk() {
        Storage.storage.put(fruit, 10);
        TransactionDto transaction = new TransactionDto("operation", "banana", 11);
        operationHandler.apply(transaction);
        int actual = Storage.storage.get(fruit);
    }

    @After
    public void afterEachTest() {
        Storage.storage.clear();
    }
}
