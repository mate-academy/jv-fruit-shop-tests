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

public class BalanceOperationTest {
    private static OperationHandler operationHandler;
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void beforeClass() {
        FruitDao fruitDao = new FruitDaoImpl();
        operationHandler = new BalanceOperation(fruitDao);
    }

    @Before
    public void setUp() {
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("banana");
    }

    @Test
    public void balanceHandler_emptyStorage_Ok() {
        fruitTransaction.setQuantity(70);
        operationHandler.handle(fruitTransaction);
        int expectedQuantityBanana = 70;
        int actualQuantityBanana = Storage.getFruitsStorage().get("banana");
        Assert.assertEquals(expectedQuantityBanana, actualQuantityBanana);
    }

    @Test
    public void balanceHandler_notEmptyStorage_Ok() {
        Storage.getFruitsStorage().put("banana", 20);
        fruitTransaction.setQuantity(150);
        operationHandler.handle(fruitTransaction);
        int expectedQuantityBanana = 150;
        int actualQuantityBanana = Storage.getFruitsStorage().get("banana");
        Assert.assertEquals(expectedQuantityBanana, actualQuantityBanana);
    }

    @After
    public void tearDown() {
        Storage.getFruitsStorage().clear();
    }
}
