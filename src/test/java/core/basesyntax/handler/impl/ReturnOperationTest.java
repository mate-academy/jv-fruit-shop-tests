package core.basesyntax.handler.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.impl.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.handler.OperationHandler;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationTest {
    private static OperationHandler operationHandler;
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void beforeClass() {
        FruitDao fruitDao = new FruitDaoImpl();
        operationHandler = new ReturnOperation(fruitDao);
    }

    @Before
    public void setUp() {
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.RETURN);
        fruitTransaction.setFruit("melon");
    }

    @Test (expected = RuntimeException.class)
    public void returnOperation_emptyStorage_notOk() {
        fruitTransaction.setQuantity(200);
        operationHandler.handle(fruitTransaction);
    }

    @Test
    public void returnOperation_notEmptyStorage() {
        Storage.getFruitsStorage().put("melon", 146);
        fruitTransaction.setQuantity(30);
        int expected = 176;
        operationHandler.handle(fruitTransaction);
        int actual = Storage.getFruitsStorage().get("melon");
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.getFruitsStorage().clear();
    }
}
