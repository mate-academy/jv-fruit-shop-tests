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

public class SupplyOperationHandlerTest {
    private static OperationHandler operationHandler;
    private static FruitTransaction fruitTransaction;
    private final static Fruit MANGO = new Fruit("mango");

    @BeforeClass
    public static void beforeClass() {
        FruitDao fruitDao = new FruitDaoImpl();
        operationHandler = new SupplyOperationHandler(fruitDao);
    }

    @Before
    public void setUp() {
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransaction.setFruit(MANGO);
    }

    @Test (expected = RuntimeException.class)
    public void supplyHandler_emptyStorage_Ok() {
        fruitTransaction.setQuantity(100);
        operationHandler.handle(fruitTransaction);
    }

    @Test
    public void supplyHandler_notEmptyStorage() {
        Storage.fruits.put(MANGO, 120);
        fruitTransaction.setQuantity(60);
        int expected = 180;
        operationHandler.handle(fruitTransaction);
        int actual = Storage.fruits.get(MANGO);
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
