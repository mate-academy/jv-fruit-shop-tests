package core.basesyntax.service.operations;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.exeptions.InvalidQuantityException;
import core.basesyntax.model.Fruit;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationDecreaseHandlerTest {
    private static OperationHandler operation;
    private static FruitDao fruitDao;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        operation = new OperationDecreaseHandler(fruitDao);
    }

    @Before
    public void setUp() {
        fruitDao.add(new Fruit("banana"), 152);
    }

    @After
    public void tearDown() {
        fruitDao.getAll().clear();
    }

    @Test
    public void operationDecreaseHandlerTest_apply_Ok() {
        Fruit banana = new Fruit("banana");
        int excepted = 100;
        int actual = operation.apply(52, banana);
        Assert.assertEquals(excepted, actual);
    }

    @Test (expected = InvalidQuantityException.class)
    public void operationDecreaseHandlerTest_apply_NotOk() {
        Fruit banana = new Fruit("banana");
        operation.apply(200, banana);
    }
}
