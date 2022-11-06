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
    private Storage storage;
    private Fruit apple;
    private FruitDao testFruitDao;
    private OperationHandler operationHandlerSupply;

    @Before
    public void setUp() {
        apple = new Fruit("apple");
        storage = new StorageImpl();
        testFruitDao = new FruitDaoImpl();
        operationHandlerSupply = new OperationHandlerSupplyImpl();
    }

    @Test
    public void applyMethod_Ok() {
        int expectedAmount = 235;
        testFruitDao.put(apple, 10);
        operationHandlerSupply.apply(apple, 225);
        int actualAmount = storage.getStorage().get(apple);
        assertEquals(expectedAmount, actualAmount);
    }

    @After
    public void deleteAll() {
        storage.getStorage().clear();
    }
}
