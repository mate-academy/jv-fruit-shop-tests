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

public class OperationHandlerReturnImplTest {
    private Storage testStorage;
    private Fruit apple;
    private FruitDao testFruitDao;
    private OperationHandler operationHandlerReturn;

    @Before
    public void setUp() {
        apple = new Fruit("apple");
        testStorage = new StorageImpl();
        testFruitDao = new FruitDaoImpl();
        operationHandlerReturn = new OperationHandlerReturnImpl();
    }

    @Test
    public void applyMethod_Ok() {
        int expectedAmount = 25;
        testFruitDao.put(apple, 10);
        operationHandlerReturn.apply(apple, 15);
        int actualAmount = testStorage.getStorage().get(apple);
        assertEquals(expectedAmount, actualAmount);
    }

    @Test(expected = RuntimeException.class)
    public void applyNegativeAmount_NotOk() {
        testFruitDao.put(apple, 10);
        operationHandlerReturn.apply(apple, -1);
    }

    @After
    public void deleteAll() {
        testStorage.getStorage().clear();
    }
}

