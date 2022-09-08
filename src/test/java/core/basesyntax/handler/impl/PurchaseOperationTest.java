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

public class PurchaseOperationTest {
    private static OperationHandler operationHandler;
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void beforeClass() {
        FruitDao fruitDao = new FruitDaoImpl();
        operationHandler = new PurchaseOperation(fruitDao);
    }

    @Before
    public void setUp() {
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit("apple");
    }

    @Test (expected = RuntimeException.class)
    public void purchaseOperation_EmptyStorage_notOk() {
        fruitTransaction.setQuantity(100);
        operationHandler.handle(fruitTransaction);
    }

    @Test
    public void purchaseOperation_notEmptyStorage_Ok() {
        Storage.getFruitsStorage().put("apple", 64);
        fruitTransaction.setQuantity(38);
        operationHandler.handle(fruitTransaction);
        int expectedQuantityApple = 26;
        int actualQuantityApple = Storage.getFruitsStorage().get("apple");
        Assert.assertEquals(expectedQuantityApple, actualQuantityApple);
    }

    @Test
    public void purchaseOperation_negativeBalance_Ok() {
        Storage.getFruitsStorage().put("apple", 10);
        fruitTransaction.setQuantity(38);
        operationHandler.handle(fruitTransaction);
        int expectedQuantityApple = -28;
        int actualQuantityApple = Storage.getFruitsStorage().get("apple");
        Assert.assertEquals(expectedQuantityApple, actualQuantityApple);
    }

    @After
    public void tearDown() {
        Storage.getFruitsStorage().clear();
    }
}
