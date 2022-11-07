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

public class OperationHandlerReturnImplTest {
    private static Storage testStorage;
    private static OperationHandler operationHandlerReturn;
    private Fruit apple;

    @BeforeClass
    public static void globalSetUp() {
        testStorage = new StorageImpl();
        operationHandlerReturn = new OperationHandlerReturnImpl();
    }

    @Before
    public void setUp() {
        apple = new Fruit("apple");
    }

    @Test
    public void applyMethod_Ok() {
        int expectedAmount = 25;
        testStorage.getStorage().put(apple, 10);
        operationHandlerReturn.apply(apple, 15);
        int actualAmount = testStorage.getStorage().get(apple);
        assertEquals(expectedAmount, actualAmount);
    }

    @Test(expected = RuntimeException.class)
    public void applyNegativeAmount_NotOk() {
        testStorage.getStorage().put(apple, 10);
        operationHandlerReturn.apply(apple, -1);
    }

    @After
    public void deleteAllDataFromStorage() {
        testStorage.getStorage().clear();
    }
}

