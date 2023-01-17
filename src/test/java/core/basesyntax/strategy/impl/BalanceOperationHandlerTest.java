package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static final String DEFAULT_FRUIT = "apple";
    private static OperationHandler operationHandler;
    private static FruitTransaction fruitTransaction;
    private static FruitDao fruitDao;

    @BeforeClass
    public static void init() {
        operationHandler = new BalanceOperationHandler();
        fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, DEFAULT_FRUIT, 20);
        fruitDao = new FruitDaoImpl();
    }

    @Test
    public void handle_saveNewValue_ok() {
        Integer expected = fruitTransaction.getQuantity();
        operationHandler.handle(fruitTransaction);
        Integer actual = fruitDao.getQuantity(fruitTransaction.getFruit());
        assertEquals(String.format("Should record %d for key \"%s\" but was %d",
                expected, DEFAULT_FRUIT, actual), expected, actual);
    }

    @Test
    public void handle_updateExistingValue_ok() {
        operationHandler.handle(fruitTransaction);
        Integer expected = 30;
        fruitTransaction.setQuantity(expected);
        operationHandler.handle(fruitTransaction);
        Integer actual = fruitDao.getQuantity(fruitTransaction.getFruit());
        assertEquals(String.format("Should record %d for key \"%s\" but was %d",
                expected, DEFAULT_FRUIT, actual), expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void handle_nullKey_notOk() {
        fruitTransaction.setFruit(null);
        operationHandler.handle(fruitTransaction);
    }

    @After
    public void tearDown() {
        fruitDao.clearStorage();
    }
}