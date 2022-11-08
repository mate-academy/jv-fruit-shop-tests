package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.db.impl.StorageImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.strategy.OperationHandler;
import java.util.Map;
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
    public void purchaseMethodTest_Ok() {
        Map<Fruit, Integer> expectedMap = Map.of(apple, 5, kiwi, 15);
        operationHandlerPurchase.apply(apple, 5);
        assertEquals(expectedMap, testStorage.getStorage());
    }

    @Test(expected = RuntimeException.class)
    public void purchaseInvalidValue_notOk() {
        operationHandlerPurchase.apply(apple, 100);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseWithNegativeValue_notOk() {
        operationHandlerPurchase.apply(apple, -100);
    }

    @After
    public void deleteAllDataFromStorage() {
        testStorage.getStorage().clear();
    }
}
