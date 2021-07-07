package core.basesyntax.strategy;

import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private OperationHandler balanceOperation = new BalanceOperationHandler();

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test(expected = NullPointerException.class)
    public void getNullTransaction_NotOk() {
        balanceOperation.apply(null);
    }

    @Test
    public void balanceOkNumber_Ok() {
        balance("coconut", 20, 20);
    }

    @Test
    public void balanceZeroNumber_Ok() {
        balance("coconut", 0, 0);
    }

    private void balance(String fruitName, int quantity, int expected) {
        Fruit fruit = new Fruit(fruitName);
        Transaction transaction = new Transaction("b", fruit.getName(), quantity);
        balanceOperation.apply(transaction);
        int actual = Storage.storage.get(fruit);
        Assert.assertEquals(expected, actual);
    }
}
