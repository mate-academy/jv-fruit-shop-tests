package core.basesyntax.service.operation.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.operation.OperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static String[] lineInfo;
    private static FruitDao fruitDao;
    private static OperationHandler operationHandler;
    private Class clazz;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        operationHandler = new PurchaseOperationHandler(fruitDao);
        lineInfo = new String[]{"s", "apple", "5"};
    }

    @Before
    public void setUp() {
        fruitDao = new FruitDaoImpl();
        operationHandler = new PurchaseOperationHandler(fruitDao);
        Storage.getFruits().clear();
    }

    @Test
    public void checkInvalidApply_Ok() {
        lineInfo[2] = "15";
        fruitDao.add("apple",10);
        try {
            operationHandler.apply(lineInfo);
        } catch (RuntimeException e) {
            clazz = RuntimeException.class;
        }
        Assert.assertEquals("RuntimeException must be thrown",
                RuntimeException.class,clazz);
    }

    @Test
    public void checkCorrectApply_Ok() {
        fruitDao.add("apple",10);
        operationHandler.apply(lineInfo);
        Assert.assertEquals(5, fruitDao.get("apple").getQuantity());
    }

    @Test
    public void checkInvalidValue_NotOk() {
        lineInfo[1] = "orange";
        try {
            operationHandler.apply(lineInfo);
        } catch (RuntimeException e) {
            clazz = RuntimeException.class;
        }
        Assert.assertEquals("RuntimeException must be thrown",
                RuntimeException.class,clazz);
    }

    @After
    public void after() {
        lineInfo = new String[]{"s", "apple", "5"};
        Storage.getFruits().clear();
    }
}
