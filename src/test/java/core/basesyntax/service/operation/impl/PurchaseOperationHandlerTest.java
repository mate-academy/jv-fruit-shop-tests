package core.basesyntax.service.operation.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.operation.OperationHandler;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private String[] lineInfo = new String[]{"s", "apple", "5"};
    private FruitDao fruitDao;
    private OperationHandler operationHandler;
    private boolean thrown = false;

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
            thrown = true;
        }
        Assert.assertTrue("We have not enough " + lineInfo[1],thrown);
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
            thrown = true;
        }
        Assert.assertTrue("The store may have " + lineInfo[1],thrown);
    }

    @AfterClass
    public static void afterClass() {
        Storage.getFruits().clear();
    }
}
