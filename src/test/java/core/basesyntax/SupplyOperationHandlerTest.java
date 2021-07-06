package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.SupplyOperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static OperationHandler handler;
    private static Fruit apple;
    private static Fruit banana;
    private static Fruit orange;

    @BeforeClass
    public static void beforeClass() {
        handler = new SupplyOperationHandler();
        apple = new Fruit("apple");
        banana = new Fruit("banana");
        orange = new Fruit("orange");
    }

    @Before
    public void setUp() {
        Storage.getStorage().put(apple, 63);
        Storage.getStorage().put(banana, 89);
    }

    @Test
    public void supplyHandler_addTransactionWithExistingFruit_Ok() {
        Transaction transaction = new Transaction("s", "apple", 134);
        handler.perform(transaction);
        int actual = Storage.getStorage().get(apple);
        int expected = 197;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void supplyHandle_addTransactionWithNotExistingFruit_Ok() {
        Transaction transaction = new Transaction("s", "orange", 77);
        handler.perform(transaction);
        int actual = Storage.getStorage().get(orange);
        int expected = 77;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void supplyHandle_chekReturnValue_Ok() {
        Transaction transaction = new Transaction("s", "banana", 32);
        int actual = handler.perform(transaction);
        int expected = 121;
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.getStorage().clear();
    }
}
