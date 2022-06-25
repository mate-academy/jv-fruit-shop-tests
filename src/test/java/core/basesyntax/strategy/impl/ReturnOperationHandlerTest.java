package core.basesyntax.strategy.impl;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.FruitsDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static FruitTransaction fruitTransaction;
    private static ReturnOperationHandler returnOperationHandler;
    private static FruitsDao fruitsDao;

    @BeforeClass
    public static void beforeClass() {
        fruitsDao = new FruitsDaoImpl();
        returnOperationHandler = new ReturnOperationHandler(fruitsDao);
    }

    @Test(expected = RuntimeException.class)
    public void handle_nullValueOfOperation_notOk() {
        returnOperationHandler.handleOperation(null);
    }

    @Test(expected = RuntimeException.class)
    public void handle_negativeAmountOfBalance_notOk() {
        fruitTransaction = new FruitTransaction("b", "orange", -125);
        returnOperationHandler.handleOperation(fruitTransaction);
    }

    @Test
    public void handle_validValue_isOk() {
        fruitsDao.add("mango", 20);
        fruitTransaction = new FruitTransaction("r", "mango", 15);
        returnOperationHandler.handleOperation(fruitTransaction);
        int actual = Storage.getFruitStorage().get("mango");
        int expected = 35;
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void handle_nullOperationValue_notOk() {
        fruitTransaction = new FruitTransaction(null, "banana", 20);
        returnOperationHandler.handleOperation(fruitTransaction);
    }
}
