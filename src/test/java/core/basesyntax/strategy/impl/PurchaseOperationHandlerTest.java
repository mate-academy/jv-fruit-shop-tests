package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static final String KEY = "apple";
    private static final Integer DEFAULT_QUANTITY = 20;
    private static OperationHandler operationHandler;
    private static FruitTransaction fruitTransaction;
    private static FruitDao fruitDao;

    @BeforeClass
    public static void init() {
        operationHandler = new PurchaseOperationHandler();
        fruitDao = new FruitDaoImpl();
        fruitTransaction = new FruitTransaction();
    }

    @Before
    public void setUp() {
        fruitTransaction.setFruit(KEY);
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitDao.updateQuantity(KEY, DEFAULT_QUANTITY);
    }

    @Test
    public void handle_purchaseSomeFruits_ok() {
        Integer fruitsBought = 8;
        Integer expected = fruitDao.getQuantity(KEY) - fruitsBought;
        fruitTransaction.setQuantity(fruitsBought);
        operationHandler.handle(fruitTransaction);
        Integer actual = fruitDao.getQuantity(fruitTransaction.getFruit());
        assertEquals(String.format("Should return %d for key \"%s\" but was %d",
                expected, KEY, actual), expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void handle_purchaseMoreThanStored_notOk() {
        Integer fruitsBought = DEFAULT_QUANTITY + 1;
        fruitTransaction.setQuantity(fruitsBought);
        operationHandler.handle(fruitTransaction);
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
