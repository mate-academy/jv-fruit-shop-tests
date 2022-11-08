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

public class OperationHandlerBalanceImplTest {
    private static OperationHandler operationHandlerBalance;
    private static Storage testStorage;
    private Fruit apple;
    private Fruit kiwi;

    @BeforeClass
    public static void globalSetUp() {
        operationHandlerBalance = new OperationHandlerSupplyImpl();
        testStorage = new StorageImpl();
    }

    @Before
    public void setUp() {
        apple = new Fruit("apple");
        kiwi = new Fruit("kiwi");

    }

    @Test
    public void createBalanceMethodTest_Ok() {
        Map<Fruit, Integer> expectedMap = Map.of(apple, 10, kiwi, 15);
        operationHandlerBalance.apply(apple, 10);
        operationHandlerBalance.apply(kiwi, 15);
        assertEquals(expectedMap, testStorage.getStorage());
    }

    @After
    public void deleteAllDataFromStorage() {
        testStorage.getStorage().clear();
    }
}
