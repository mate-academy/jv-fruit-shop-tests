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

public class OperationHandlerSupplyImplTest {
    private static Storage testStorage;
    private static OperationHandler operationHandlerSupply;
    private Fruit apple;

    @BeforeClass
    public static void globalSetUp() {
        testStorage = new StorageImpl();
        operationHandlerSupply = new OperationHandlerSupplyImpl();
    }

    @Before
    public void setUp() {
        apple = new Fruit("apple");
        testStorage.getStorage().put(apple, 10);
    }

    @Test
    public void applyMethod_Ok() {
        int expectedAmount = 235;
        operationHandlerSupply.apply(apple, 225);
        int actualAmount = testStorage.getStorage().get(apple);
        assertEquals(expectedAmount, actualAmount);
    }

    @Test(expected = RuntimeException.class)
    public void applyNegativeAmount_NotOk() {
        operationHandlerSupply.apply(apple, -1);
    }

    @Test
    public void applyWithoutCurrentFruitInStorage() {
        int expectedAmount = 225;
        testStorage.getStorage().clear();
        operationHandlerSupply.apply(apple, 225);
        int actualAmount = testStorage.getStorage().get(apple);
        assertEquals(expectedAmount, actualAmount);
    }

    @After
    public void deleteAllDataFromStorage() {
        testStorage.getStorage().clear();
    }
}
