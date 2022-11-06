package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.db.impl.StorageImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.strategy.OperationHandler;
import org.junit.Before;
import org.junit.Test;

public class OperationHandlerReturnImplTest {
    private Storage storage;
    private Fruit apple;
    private FruitDao testFruitDao;
    private OperationHandler operationHandlerReturn;

    @Before
    public void setUp() {
        apple = new Fruit("apple");
        storage = new StorageImpl();
        testFruitDao = new FruitDaoImpl();
        operationHandlerReturn = new OperationHandlerReturnImpl();
    }

    @Test
    public void applyMethod_Ok() {
        int expectedAmount = 25;
        testFruitDao.put(apple, 10);
        operationHandlerReturn.apply(apple, 15);
        int actualAmount = storage.getStorage().get(apple);
        assertEquals(expectedAmount, actualAmount);
    }
}

