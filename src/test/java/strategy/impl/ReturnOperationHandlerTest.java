package strategy.impl;

import db.Storage;
import model.Fruit;
import model.Transaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import strategy.OperationHandler;

public class ReturnOperationHandlerTest {
    private static OperationHandler operationHandler;
    private static Transaction transaction;
    private static Fruit fruit;

    @BeforeClass
    public static void setUp() {
        operationHandler = new ReturnOperationHandler();
        fruit = new Fruit("peach");
        transaction = new Transaction();
        transaction.setOperation("r");
        transaction.setFruit(fruit);
        transaction.setQuantity(120);
    }

    @Test
    public void process_correctQuantity_ok() {
        operationHandler.process(transaction.getFruit(), transaction.getQuantity());
        int expected = 120;
        int actual = Storage.storage.get(fruit);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void process_correctQuantityBeforeNullStored_ok() {
        Storage.storage.put(fruit, null);
        operationHandler.process(transaction.getFruit(), transaction.getQuantity());
        int expected = 120;
        int actual = Storage.storage.get(fruit);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void process_preexistedQuantity_ok() {
        Storage.storage.put(fruit, 1);
        operationHandler.process(transaction.getFruit(), transaction.getQuantity());
        int expected = 121;
        int actual = Storage.storage.get(fruit);
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
