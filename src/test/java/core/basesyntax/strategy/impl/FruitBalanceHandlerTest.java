package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitBalanceHandlerTest {
    private static OperationHandler operationHandler;
    private static Fruit fruit;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new FruitBalanceHandler(new FruitDaoImpl());
        fruit = new Fruit("banana");
    }

    @Test
    public void proceed_isValid() {
        operationHandler.proceed(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, fruit, 10));
        int actual = Storage.fruits.get(fruit);
        int expected = 10;
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void proceed_isNotValid() {
        operationHandler.proceed(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        null,
                        10)
        );
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
