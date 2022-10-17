package core.basesyntax.strategy.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.impl.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler operationHandler;
    private static FruitTransaction fruitTransaction;
    private final static Fruit BANANA = new Fruit("banana");

    @BeforeClass
    public static void beforeClass() {
        FruitDao fruitDao = new FruitDaoImpl();
        operationHandler = new BalanceOperationHandler(fruitDao);
    }

    @Before
    public void setUp() {
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit(BANANA);
    }

    @Test
    public void balanceHandler_emptyStorage_Ok() {
        fruitTransaction.setQuantity(30);
        operationHandler.handle(fruitTransaction);
        int expectedQuantityBanana = 30;
        int actualQuantityBanana = Storage.fruits.get(BANANA);
        Assert.assertEquals(expectedQuantityBanana, actualQuantityBanana);
    }

    @Test
    public void balanceHandler_notEmptyStorage_Ok() {
        Storage.fruits.put(BANANA, 25);
        fruitTransaction.setQuantity(120);
        operationHandler.handle(fruitTransaction);
        int expectedQuantityBanana = 120;
        int actualQuantityBanana = Storage.fruits.get(BANANA);
        Assert.assertEquals(expectedQuantityBanana, actualQuantityBanana);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
