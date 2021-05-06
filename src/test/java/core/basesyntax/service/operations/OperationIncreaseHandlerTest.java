package core.basesyntax.service.operations;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.exeptions.InvalidQuantityException;
import core.basesyntax.model.Fruit;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationIncreaseHandlerTest {
    private static OperationHandler operation;
    private static FruitDao fruitDao;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        operation = new OperationIncreaseHandler(fruitDao);
    }

    @After
    public void tearDown() {
        fruitDao.getAll().clear();
    }

    @Test
    public void operationIncreaseHandlerTest_apply_Ok() {
        saveToStorage();
        Fruit banana = new Fruit("banana");
        int expected = 160;
        int actual = operation.apply(8, banana);
        Assert.assertEquals(expected, actual);
    }

    @Test (expected = InvalidQuantityException.class)
    public void operationIncreaseHandlerTest_apply_NotOk() {
        Fruit banana = new Fruit("banana");
        int actual = operation.apply(-10, banana);
    }

    private static void saveToStorage() {
        Fruit banana = new Fruit("banana");
        fruitDao.add(banana, 152);
    }
}
