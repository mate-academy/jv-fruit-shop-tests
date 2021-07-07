package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler handler;
    private static Fruit apple;
    private static Fruit banana;
    private static Fruit orange;

    @BeforeClass
    public static void beforeClass() {
        handler = new BalanceOperationHandler();
        apple = new Fruit("apple");
        banana = new Fruit("banana");
        orange = new Fruit("orange");
    }

    @Before
    public void setUp() {
        Storage.getStorage().put(apple, 45);
        Storage.getStorage().put(banana, 77);
    }

    @Test
    public void balanceHandler_addTransactionWithExistingFruit_Ok() {
        Transaction transaction = new Transaction("b", "apple", 100);
        handler.perform(transaction);
        int actual = Storage.getStorage().get(apple);
        int expected = 100;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void balanceHandler_addTransactionWithNotExistingFruit_Ok() {
        Transaction transaction = new Transaction("b", "orange", 45);
        handler.perform(transaction);
        int actual = Storage.getStorage().get(orange);
        int expected = 45;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void balanceHandler_checkReturnValue_Ok() {
        Transaction transaction = new Transaction("b", "banana", 24);
        int actual = handler.perform(transaction);
        int expected = 24;
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.getStorage().clear();
    }
}
