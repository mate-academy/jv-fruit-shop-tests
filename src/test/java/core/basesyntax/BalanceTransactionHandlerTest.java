package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.FruitTransaction;
import core.basesyntax.transactionhandler.BalanceTransactionHandler;
import core.basesyntax.transactionhandler.TransactionHandler;
import org.junit.Assert;
import org.junit.Test;

public class BalanceTransactionHandlerTest {
    private static final int testQuantity = 3;
    private static final Fruit testFruit = new Fruit("banana");

    @Test
    public void balance_isOk() {
        FruitTransaction fruitTransaction =
                new FruitTransaction("b", testFruit, testQuantity);
        TransactionHandler transactionHandler = new BalanceTransactionHandler();
        transactionHandler.operate(fruitTransaction);
        Integer actual = Storage.fruits.get(testFruit);
        Assert.assertEquals((int) actual, testQuantity);
    }
}
