package strategy;

import static org.junit.Assert.assertEquals;

import db.Storage;
import dto.Transaction;
import model.Fruit;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void operationBeforeTest() {
        operationHandler = new BalanceOperationHandler();
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
    public void apply_balanceApple_Ok() {
        Transaction actualTransaction = new Transaction("b", "apple", 5);
        int actual = operationHandler.apply(actualTransaction);
        assertEquals(5, actual);
    }

    @Test
    public void apply_balanceGrape_Ok() {
        Transaction actualTransaction = new Transaction("b", "grape", 40);
        int actual = operationHandler.apply(actualTransaction);
        assertEquals(40, actual);
    }
}
