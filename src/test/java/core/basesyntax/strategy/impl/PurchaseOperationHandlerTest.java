package core.basesyntax.strategy.impl;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.FruitsDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {

    private static FruitTransaction fruitTransaction;
    private static PurchaseOperationHandler purchaseOperationHandler;
    private static FruitsDao fruitsDao;

    @BeforeClass
    public static void beforeClass() {
        fruitsDao = new FruitsDaoImpl();
        purchaseOperationHandler = new PurchaseOperationHandler(fruitsDao);
    }

    @Test(expected = RuntimeException.class)
    public void handle_nullValueOfOperation_notOk() {
        purchaseOperationHandler.handleOperation(null);
    }

    @Test(expected = RuntimeException.class)
    public void handle_fruitIsNotPresent_notOK() {
        fruitTransaction = new FruitTransaction("p", "cucumber", 20);
        purchaseOperationHandler.handleOperation(fruitTransaction);
    }

    @Test(expected = RuntimeException.class)
    public void handle_negativeAmountOfBalance_notOk() {
        fruitTransaction = new FruitTransaction("p", "orange", -125);
        purchaseOperationHandler.handleOperation(fruitTransaction);
    }

    @Test(expected = RuntimeException.class)
    public void handle_notEnoughQuantityOfFruits_notOk() {
        fruitsDao.add("orange", 5);
        fruitTransaction = new FruitTransaction("p", "orange", 15);
        purchaseOperationHandler.handleOperation(fruitTransaction);
    }

    @Test
    public void handle_validValue_isOk() {
        fruitsDao.add("banana", 55);
        fruitTransaction = new FruitTransaction("p", "banana", 20);
        purchaseOperationHandler.handleOperation(fruitTransaction);
        int actual = Storage.getFruitStorage().get("banana");
        int expected = 35;
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void handle_nullOperationValue_notOk() {
        fruitTransaction = new FruitTransaction(null, "banana", 20);
        purchaseOperationHandler.handleOperation(fruitTransaction);
    }
}
