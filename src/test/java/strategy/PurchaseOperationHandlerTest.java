package strategy;

import static org.junit.Assert.assertEquals;

import db.Storage;
import model.Fruit;
import model.TransactionDto;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new PurchaseOperationHandler();
    }

    @Before
    public void init() {
        Storage.storage.clear();
    }

    @Test
    public void purchaseOperation_correctWorkOperation_ok() {
        int expected = 80;
        Fruit fruit = new Fruit("banana");
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
        Fruit fruit = new Fruit("banana");
        Storage.storage.put(fruit, 10);
        TransactionDto transaction = new TransactionDto("operation", "banana", 10);
        operationHandler.apply(transaction);
        int actual = Storage.storage.get(fruit);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseOperation_purchaseMuchThanHave_notOk() {
        Fruit fruit = new Fruit("banana");
        Storage.storage.put(fruit, 10);
        TransactionDto transaction = new TransactionDto("operation", "banana", 11);
        operationHandler.apply(transaction);
        int actual = Storage.storage.get(fruit);
    }
}
