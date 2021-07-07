package strategy;

import static org.junit.Assert.assertEquals;

import db.Storage;
import dto.Transaction;
import model.Fruit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AddOperationHandlerTest {
    private static OperationHandler operationHandler;

    @Before
    public void operationsBeforeTest() {
        Fruit apple = new Fruit("apple");
        Storage.storage.put(apple, 25);
        operationHandler = new AddOperationHandler();
    }

    @After
    public void operationAfterTest() {
        Storage.storage.clear();
    }

    @Test
    public void apply_supplyApple_Ok() {
        Transaction actualTransaction = new Transaction("s", "apple", 50);
        int actual = operationHandler.apply(actualTransaction);
        assertEquals(75, actual);
    }

    @Test
    public void apply_returnApple_Ok() {
        Transaction actualTransaction = new Transaction("r", "apple", 15);
        int actual = operationHandler.apply(actualTransaction);
        assertEquals(40, actual);
    }

    @Test
    public void apply_supplyBanana_Ok() {
        Transaction actualTransaction = new Transaction("s", "banana", 50);
        int actual = operationHandler.apply(actualTransaction);
        assertEquals(50, actual);
    }

    @Test
    public void apply_returnBanana_Ok() {
        Transaction actualTransaction = new Transaction("r", "banana", 15);
        int actual = operationHandler.apply(actualTransaction);
        assertEquals(15, actual);
    }
}
