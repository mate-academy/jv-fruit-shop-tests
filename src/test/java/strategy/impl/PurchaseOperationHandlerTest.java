package strategy.impl;

import db.Storage;
import model.Fruit;
import model.Transaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import strategy.OperationHandler;

public class PurchaseOperationHandlerTest {
    private static OperationHandler operationHandler;
    private static Transaction transaction;
    private static Fruit fruit;

    @BeforeClass
    public static void setUp() {
        operationHandler = new PurchaseOperationHandler();
        fruit = new Fruit("peach");
        transaction = new Transaction();
        transaction.setOperation("p");
        transaction.setFruit(fruit);
        transaction.setQuantity(100);
    }

    @Test
    public void process_correctQuantityAfter_ok() {
        Storage.storage.put(fruit, 30000);
        transaction.setQuantity(29999);
        operationHandler.process(transaction.getFruit(), transaction.getQuantity());
        int expected = 1;
        int actual = Storage.storage.get(fruit);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void process_nullQuantityStoredBefore_notOk() {
        Storage.storage.put(fruit, null);
        operationHandler.process(transaction.getFruit(), transaction.getQuantity());
    }

    @Test(expected = RuntimeException.class)
    public void process_notEnaughOfFruit_notOk() {
        Storage.storage.put(fruit, 200);
        transaction.setQuantity(201);
        operationHandler.process(transaction.getFruit(), transaction.getQuantity());
    }

    @Test
    public void process_equalQuantity_ok() {
        Storage.storage.put(fruit, 500);
        transaction.setQuantity(500);
        operationHandler.process(transaction.getFruit(), transaction.getQuantity());
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
