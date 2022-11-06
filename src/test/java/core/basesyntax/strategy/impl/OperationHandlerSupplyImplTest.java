package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.db.impl.StorageImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OperationHandlerSupplyImplTest {
    private Storage testStorage;
    private Fruit apple;
    private FruitDao testFruitDao;
    private OperationHandler operationHandlerSupply;

    @Before
    public void setUp() {
        apple = new Fruit("apple");
        testStorage = new StorageImpl();
        testFruitDao = new FruitDaoImpl();
        operationHandlerSupply = new OperationHandlerSupplyImpl();
        testFruitDao.put(apple, 10);
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
