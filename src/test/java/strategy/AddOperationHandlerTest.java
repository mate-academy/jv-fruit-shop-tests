package strategy;

import static org.junit.Assert.assertEquals;

import db.Storage;
import model.Fruit;
import model.TransactionDto;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AddOperationHandlerTest {
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new AddOperationHandler();
    }

    @Before
    public void init() {
        Storage.storage.clear();
    }

    @Test
    public void addOperation_correctWorkOperation_ok() {
        boolean expected = true;
        Fruit fruit = new Fruit("banana");
        Storage.storage.put(fruit, 100);
        TransactionDto transaction = new TransactionDto("operation", "banana", 50);
        operationHandler.apply(transaction);
        boolean actual = Storage.storage.get(fruit) == 150;
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void addOperation_exceedMaxValue_notOk() {
        Fruit fruit = new Fruit("banana");
        Storage.storage.put(fruit, Integer.MAX_VALUE);
        TransactionDto transaction = new TransactionDto("operation", "banana", 1);
        operationHandler.apply(transaction);
    }
}
