package core.basesyntax.service.operations;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.exeptions.InvalidQuantityException;
import core.basesyntax.model.Fruit;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationDecreaseHandlerTest {
    private static OperationHandler operation;
    private static FruitDao fruitDao;

    @BeforeClass
    public static void beforeClass() throws Exception {
        fruitDao = new FruitDaoImpl();
        operation = new OperationDecreaseHandler(fruitDao);
    }

    @After
    public void tearDown() throws Exception {
        fruitDao.getAll().clear();
    }

    @Test
    public void OperationDecreaseHandlerTest_Apply_Ok() {
        saveToStorage();
        Fruit banana = new Fruit("banana");
        int excepted = 100;
        int actual = operation.apply(52, banana);
        Assert.assertEquals(excepted, actual);
    }

    @Test (expected = InvalidQuantityException.class)
    public void OperationDecreaseHandlerTest_Apply_NotOk() {
        saveToStorage();
        Fruit banana = new Fruit("banana");
        operation.apply(200, banana);
    }

    private static void saveToStorage() {
        Fruit banana = new Fruit("banana");
        fruitDao.add(banana, 152);
    }
}