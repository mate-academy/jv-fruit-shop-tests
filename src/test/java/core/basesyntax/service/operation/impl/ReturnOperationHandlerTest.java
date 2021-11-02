package core.basesyntax.service.operation.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.operation.OperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static String[] lineInfo;
    private static FruitDao fruitDao;
    private static OperationHandler operationHandler;
    private Class clazz;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        operationHandler = new ReturnOperationHandler(fruitDao);
        lineInfo = new String[]{"s", "apple", "30"};
    }

    @Test
    public void checkCorrectApply_Ok() {
        fruitDao.add("apple",0);
        operationHandler.apply(lineInfo);
        Assert.assertEquals(30, fruitDao.get("apple").getQuantity());
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
                RuntimeException.class,clazz = RuntimeException.class);
    }

    @After
    public void after() {
        lineInfo = new String[]{"s", "apple", "30"};
        Storage.getFruits().clear();
    }
}
