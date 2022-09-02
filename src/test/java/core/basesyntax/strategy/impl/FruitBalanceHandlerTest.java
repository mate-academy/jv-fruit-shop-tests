package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitBalanceHandlerTest {
    private static OperationHandler operationHandler;
    private static FruitDao fruitDao;
    private static Fruit fruit;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new FruitBalanceHandler(new FruitDaoImpl());
        fruitDao = new FruitDaoImpl();
        fruit = new Fruit("banana");
    }

    @Test
    public void proceed_isValid() {
        Storage.fruits.put(fruit, 10);
        Map<Fruit, Integer> actual = fruitDao.getAll();
        Map<Fruit, Integer> expected = Map.of(fruit, 10);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void proceed_isNotValid() {
        operationHandler.proceed(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        null,
                        10)
        );
        assertNull("Expected NPE", Storage.fruits.put(fruit, 10));
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
