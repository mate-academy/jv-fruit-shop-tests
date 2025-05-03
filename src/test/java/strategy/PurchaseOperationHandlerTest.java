package strategy;

import static org.junit.Assert.assertEquals;

import db.Storage;
import dto.Transaction;
import model.Fruit;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void operationBeforeTest() {
        operationHandler = new PurchaseOperationHandler();
    }

    @Before
    public void operationsBeforeEveryoneTest() {
        Fruit apple = new Fruit("apple");
        Storage.storage.put(apple, 25);
    }

    @After
    public void operationAfterTest() {
        Storage.storage.clear();
    }

    @Test
    public void apply_purchaseApple_Ok() {
        Transaction actualTransaction = new Transaction("p", "apple", 5);
        int actual = operationHandler.apply(actualTransaction);
        assertEquals(20, actual);
    }

    @Test(expected = RuntimeException.class)
    public void apply_purchaseApple_NotOk() {
        Transaction actualTransaction = new Transaction("p", "apple", 35);
        operationHandler.apply(actualTransaction);
    }

    @Test(expected = RuntimeException.class)
    public void apply_purchaseOrange_NotOk() {
        Transaction actualTransaction = new Transaction("p", "orange", 26);
        operationHandler.apply(actualTransaction);
    }
}
