package strategy;

import static org.junit.Assert.assertEquals;

import db.Storage;
import model.Fruit;
import model.TransactionDto;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AddOperationHandlerTest {
    private static OperationHandler operationHandler;
    private final Fruit fruit = new Fruit("banana");

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new AddOperationHandler();
    }

    @Before
    public void init() {
        Storage.storage.put(fruit, 100);
    }

    @Test
    public void addOperation_correctWorkOperation_ok() {
        int expected = 150;
        Storage.storage.put(fruit, 100);
        TransactionDto transaction = new TransactionDto("operation", "banana", 50);
        operationHandler.apply(transaction);
        int actual = Storage.storage.get(fruit);
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void addOperation_exceedMaxValue_notOk() {
        Storage.storage.put(fruit, Integer.MAX_VALUE);
        TransactionDto transaction = new TransactionDto("operation", "banana", 1);
        operationHandler.apply(transaction);
    }

    @After
    public void afterEachTest() {
        Storage.storage.clear();
    }
}
