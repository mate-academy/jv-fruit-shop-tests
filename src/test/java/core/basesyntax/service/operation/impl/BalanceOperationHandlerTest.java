package core.basesyntax.service.operation.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.operation.OperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BalanceOperationHandlerTest {
    private static String[] lineInfo;
    private static FruitDao fruitDao;
    private static OperationHandler operationHandler;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        operationHandler = new BalanceOperationHandler(fruitDao);
        lineInfo = new String[]{"s", "apple", "30"};
    }

    @Test
    public void checkCorrectApply_Ok() {
        operationHandler.apply(lineInfo);
        Assert.assertEquals(30, fruitDao.get("apple").getQuantity());
    }

    @Test
    public void checkInvalidValue_NotOk() {
        lineInfo[1] = "orange";
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Invalid fruit " + lineInfo[1]);
        operationHandler.apply(lineInfo);
    }

    @After
    public void afterClass() {
        lineInfo = new String[]{"b", "apple", "30"};
        Storage.getFruits().clear();
    }
}
