package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseOperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler handler;
    private static Fruit apple;
    private static Fruit banana;

    @BeforeClass
    public static void beforeClass() {
        handler = new PurchaseOperationHandler();
        apple = new Fruit("apple");
        banana = new Fruit("banana");
    }

    @Before
    public void setUp() {
        Storage.getStorage().put(apple, 20);
        Storage.getStorage().put(banana, 10);
    }

    @Test
    public void purchaseHandler_addTransactionWithEnoughQuantity_Ok() {
        Transaction transaction = new Transaction("p", "apple", 1);
        handler.perform(transaction);
        int actual = Storage.getStorage().get(apple);
        int expected = 19;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void purchaseHandler_checkReturnValue_Ok() {
        Transaction transaction = new Transaction("p", "banana", 6);
        int actual = handler.perform(transaction);
        int expected = 4;
        Assert.assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void purchaseHandler_addTransactionWithNotEnoughQuantity_notOk() {
        Transaction transaction = new Transaction("p", "banana", 11);
        handler.perform(transaction);
    }

    @After
    public void tearDown() {
        Storage.getStorage().clear();
    }
}
