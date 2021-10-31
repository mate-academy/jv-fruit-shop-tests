package core.basesyntax.service.operation.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.operation.OperationHandler;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private String[] lineInfo = new String[]{"b", "apple", "30"};
    private FruitDao fruitDao;
    private OperationHandler operationHandler;
    private boolean thrown = false;

    @Before
    public void setUp() {
        fruitDao = new FruitDaoImpl();
        operationHandler = new BalanceOperationHandler(fruitDao);
        Storage.getFruits().clear();
    }

    @Test
    public void checkCorrectApply_Ok() {
        operationHandler.apply(lineInfo);
        Assert.assertEquals(30, fruitDao.get("apple").getQuantity());
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
