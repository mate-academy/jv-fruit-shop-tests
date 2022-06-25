package core.basesyntax.strategy.impl;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.FruitsDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static FruitsDao fruitsDao;
    private static SupplyOperationHandler supplyOperationHandler;
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void beforeClass() {
        fruitsDao = new FruitsDaoImpl();
        supplyOperationHandler = new SupplyOperationHandler(fruitsDao);
        fruitsDao.add("mango", 20);
    }

    @Test(expected = RuntimeException.class)
    public void handle_nullValueOfOperation_notOk() {
        supplyOperationHandler.handleOperation(null);
    }

    @Test(expected = RuntimeException.class)
    public void handle_negativeAmountOfBalance_notOk() {
        fruitTransaction = new FruitTransaction("s", "orange", -125);
        supplyOperationHandler.handleOperation(fruitTransaction);
    }

    @Test
    public void handle_validValue_isOk() {
        fruitTransaction = new FruitTransaction("s", "mango", 15);
        supplyOperationHandler.handleOperation(fruitTransaction);
        int actual = Storage.getFruitStorage().get("mango");
        int expected = 35;
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void handle_nullOperationValue_notOk() {
        fruitTransaction = new FruitTransaction(null, "banana", 20);
        supplyOperationHandler.handleOperation(fruitTransaction);
    }
}
