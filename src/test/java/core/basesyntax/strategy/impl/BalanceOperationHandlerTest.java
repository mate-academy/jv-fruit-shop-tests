package core.basesyntax.strategy.impl;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.FruitsDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static FruitTransaction fruitTransaction;
    private static BalanceOperationHandler balanceOperationHandler;
    private static FruitsDao fruitsDao;

    @BeforeClass
    public static void beforeClass() {
        fruitsDao = new FruitsDaoImpl();
        balanceOperationHandler = new BalanceOperationHandler(fruitsDao);
    }

    @Test(expected = RuntimeException.class)
    public void handle_nullValueOfOperation_notOk() {
        balanceOperationHandler.handleOperation(null);
    }

    @Test
    public void handle_validValue_isOk() {
        fruitTransaction = new FruitTransaction("b", "banana", 20);
        balanceOperationHandler.handleOperation(fruitTransaction);
        int actual = Storage.getFruitStorage().get("banana");
        int expected = 20;
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void handle_nullOperationValue_notOk() {
        fruitTransaction = new FruitTransaction(null, "banana", 20);
        balanceOperationHandler.handleOperation(fruitTransaction);
    }

    @Test(expected = RuntimeException.class)
    public void handle_negativeAmountOfBalance_notOk() {
        fruitTransaction = new FruitTransaction("b", "orange", -125);
        balanceOperationHandler.handleOperation(fruitTransaction);
    }
}
