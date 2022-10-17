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

public class ReturnOperationHandlerTest {
    private static OperationHandler operationHandler;
    private static FruitTransaction fruitTransaction;
    private final static Fruit ORANGE = new Fruit("orange");

    @BeforeClass
    public static void beforeClass() {
        FruitDao fruitDao = new FruitDaoImpl();
        operationHandler = new ReturnOperationHandler(fruitDao);
    }

    @Before
    public void setUp() {
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.RETURN);
        fruitTransaction.setFruit(ORANGE);
    }

    @Test (expected = RuntimeException.class)
    public void returnHandler_emptyStorage_notOk() {
        fruitTransaction.setQuantity(100);
        operationHandler.handle(fruitTransaction);
    }

    @Test
    public void returnHandler_notEmptyStorage() {
        Storage.fruits.put(ORANGE, 120);
        fruitTransaction.setQuantity(60);
        int expected = 180;
        operationHandler.handle(fruitTransaction);
        int actual = Storage.fruits.get(ORANGE);
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
