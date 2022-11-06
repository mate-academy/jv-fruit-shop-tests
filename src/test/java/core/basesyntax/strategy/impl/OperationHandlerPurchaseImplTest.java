package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.db.impl.StorageImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.strategy.OperationHandler;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class OperationHandlerPurchaseImplTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private Fruit apple;
    private Fruit kiwi;
    private Storage storage;
    private FruitDao fruitDao;
    private OperationHandler operationHandlerPurchase;

    @Before
    public void setUp() {
        apple = new Fruit("apple");
        kiwi = new Fruit("kiwi");
        storage = new StorageImpl();
        fruitDao = new FruitDaoImpl();
        fruitDao.put(apple, 10);
        fruitDao.put(kiwi, 15);
        operationHandlerPurchase = new OperationHandlerPurchaseImpl();
    }

    @Test
    public void applyMethodTest_Ok() {
        int expectedAmountOfApple = 5;
        operationHandlerPurchase.apply(apple, 5);
        int actualAmount = storage.getStorage().get(apple);
        assertEquals(expectedAmountOfApple, actualAmount);
    }

    @Test
    public void applyInvalidValur_notOk() {
        expectedException.expect(RuntimeException.class);
        operationHandlerPurchase.apply(apple, 100);
    }
}
