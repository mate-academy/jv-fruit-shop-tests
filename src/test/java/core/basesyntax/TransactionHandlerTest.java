package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.FruitTransaction;
import core.basesyntax.transactionhandler.BalanceTransactionHandler;
import core.basesyntax.transactionhandler.PurchaseTransactionHandler;
import core.basesyntax.transactionhandler.ReturnSupplyTransactionHandler;
import core.basesyntax.transactionhandler.TransactionHandler;
import org.junit.Assert;
import org.junit.Test;

public class TransactionHandlerTest {

    private static final int testQuantity = 3;
    private static final Fruit testFruit = new Fruit("banana");
    private FruitTransaction fruitTransaction;

    @Test
    public void balance_isOk() {
        fruitTransaction =
                new FruitTransaction("b", testFruit, testQuantity);
        TransactionHandler transactionHandler = new BalanceTransactionHandler();
        transactionHandler.operate(fruitTransaction);
        Integer actual = Storage.fruits.get(testFruit);
        Assert.assertEquals((int) actual, testQuantity);
    }

    @Test
    public void purchase_isOk() {
        Storage.fruits.put(testFruit, 5);
        fruitTransaction =
                new FruitTransaction("p", testFruit, testQuantity);
        TransactionHandler transactionHandler = new PurchaseTransactionHandler();
        transactionHandler.operate(fruitTransaction);
        Assert.assertEquals((int) Storage.fruits.get(testFruit), 5 - testQuantity);
    }

    @Test (expected = RuntimeException.class)
    public void purchase_notOk() {
        Storage.fruits.put(testFruit, 2);
        fruitTransaction =
                new FruitTransaction("p", testFruit, testQuantity);
        TransactionHandler transactionHandler = new PurchaseTransactionHandler();
        transactionHandler.operate(fruitTransaction);
    }

    @Test
    public void return_isOk() {
        Storage.fruits.put(testFruit, 2);
        fruitTransaction =
                new FruitTransaction("p", testFruit, testQuantity);
        TransactionHandler transactionHandler = new ReturnSupplyTransactionHandler();
        transactionHandler.operate(fruitTransaction);
        Assert.assertEquals((int) Storage.fruits.get(testFruit), 5);
    }
}
