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
        testStorage.getStorage().put(apple, 10);
    }

    @Test
    public void returnMethod_ok() {
        Map<Fruit, Integer> expectedMap = Map.of(apple, 25);
        operationHandlerReturn.apply(apple, 15);
        assertEquals(expectedMap, testStorage.getStorage());
    }

    @Test(expected = RuntimeException.class)
    public void returnNegativeAmount_notOk() {
        operationHandlerReturn.apply(apple, -1);
    }

    @After
    public void deleteAllDataFromStorage() {
        testStorage.getStorage().clear();
    }
}

