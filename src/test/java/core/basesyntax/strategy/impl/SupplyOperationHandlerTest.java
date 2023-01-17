package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static final String KEY = "apple";
    private static final Integer DEFAULT_QUANTITY = 20;
    private static OperationHandler operationHandler;
    private static FruitTransaction fruitTransaction;
    private static FruitDao fruitDao;

    @BeforeClass
    public static void init() {
        operationHandler = new SupplyOperationHandler();
        fruitDao = new FruitDaoImpl();
        fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, KEY, DEFAULT_QUANTITY);
    }

    @Test
    public void handle_supplySomeFruits_ok() {
        fruitDao.updateQuantity(KEY, DEFAULT_QUANTITY);
        Integer fruitsSupplied = DEFAULT_QUANTITY;
        Integer expected = fruitDao.getQuantity(KEY) + fruitsSupplied;
        fruitTransaction.setQuantity(fruitsSupplied);
        operationHandler.handle(fruitTransaction);
        Integer actual = fruitDao.getQuantity(fruitTransaction.getFruit());
        assertEquals(String.format("Should return %d for key \"%s\" but was %d",
                expected, KEY, actual), expected, actual);
    }

    @Test
    public void handle_returnWithEmptyStorage_ok() {
        Integer expected = DEFAULT_QUANTITY;
        fruitTransaction.setQuantity(expected);
        operationHandler.handle(fruitTransaction);
        Integer actual = fruitDao.getQuantity(fruitTransaction.getFruit());
        assertEquals(String.format("Should return %d for key \"%s\" but was %d",
                expected, KEY, actual), expected, actual);
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
