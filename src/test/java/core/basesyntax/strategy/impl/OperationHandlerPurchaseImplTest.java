package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.db.impl.StorageImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationHandlerPurchaseImplTest {
    private static Storage testStorage;
    private static OperationHandler operationHandlerPurchase;
    private Fruit apple;
    private Fruit kiwi;

    @BeforeClass
    public static void globalSetUp() {
        testStorage = new StorageImpl();
        operationHandlerPurchase = new OperationHandlerPurchaseImpl();
    }

    @Before
    public void setUp() {
        apple = new Fruit("apple");
        kiwi = new Fruit("kiwi");
        testStorage.getStorage().put(apple, 10);
        testStorage.getStorage().put(kiwi, 15);
    }

    @Test
    public void applyMethodTest_Ok() {
        int expectedAmountOfApple = 5;
        operationHandlerPurchase.apply(apple, expectedAmountOfApple);
        int actualAmount = testStorage.getStorage().get(apple);
        assertEquals(expectedAmountOfApple, actualAmount);
    }

    @Test(expected = RuntimeException.class)
    public void applyInvalidValue_NotOk() {
        operationHandlerPurchase.apply(apple, 100);
    }

    @Test(expected = RuntimeException.class)
    public void applyNegativeValue_NotOk() {
        operationHandlerPurchase.apply(apple, -100);
    }

    @After
    public void deleteAllDataFromStorage() {
        testStorage.getStorage().clear();
    }
}
