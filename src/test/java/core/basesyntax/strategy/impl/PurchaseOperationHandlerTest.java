package core.basesyntax.strategy.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.impl.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private final static Fruit APPLE = new Fruit("apple");
    private static OperationHandler operationHandler;
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void beforeClass() {
        FruitDao fruitDao = new FruitDaoImpl();
        operationHandler = new PurchaseOperationHandler(fruitDao);
    }

    @Before
    public void setUp() {
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit(APPLE);
    }

    @Test (expected = RuntimeException.class)
    public void purchaseOperation_EmptyStorage_notOk() {
        fruitTransaction.setQuantity(100);
        operationHandler.handle(fruitTransaction);
    }

    @Test
    public void purchaseHandler_notEmptyStorage_Ok() {
        Storage.fruits.put(APPLE, 120);
        fruitTransaction.setQuantity(56);
        operationHandler.handle(fruitTransaction);
        int expectedQuantityApple = 64;
        int actualQuantityApple = Storage.fruits.get(APPLE);
        Assert.assertEquals(expectedQuantityApple, actualQuantityApple);
    }

    @Test
    public void purchaseHandler_negativeFruitBalance_Ok() {
        Storage.fruits.put(APPLE, 10);
        fruitTransaction.setQuantity(20);
        operationHandler.handle(fruitTransaction);
        int expectedQuantityApple = -10;
        int actualQuantityApple = Storage.fruits.get(APPLE);
        Assert.assertEquals(expectedQuantityApple, actualQuantityApple);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}