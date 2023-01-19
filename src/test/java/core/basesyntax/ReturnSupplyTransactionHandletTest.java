package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.FruitTransaction;
import core.basesyntax.transactionhandler.ReturnSupplyTransactionHandler;
import core.basesyntax.transactionhandler.TransactionHandler;
import org.junit.Assert;
import org.junit.Test;

public class ReturnSupplyTransactionHandletTest {
    private static final int testQuantity = 3;
    private static final Fruit testFruit = new Fruit("banana");
    private FruitTransaction fruitTransaction;

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
