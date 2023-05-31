package core.basesyntax.strategy.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    @Test
    public void setBalanceNewFruit_Ok() {
        Fruit someFruit = new Fruit("someFruit");
        Transaction balanceTransaction = new Transaction("Balance", someFruit, 10);
        new BalanceOperationHandler().apply(balanceTransaction);
        Integer expected = 10;
        Integer actual = Storage.storage.get(someFruit);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void setBalanceExistingFruit_Ok() {
        BalanceOperationHandler handler = new BalanceOperationHandler();
        Fruit someFruit = new Fruit("someFruit");
        Transaction someBalanceTransaction = new Transaction("b", someFruit, 10);
        handler.apply(someBalanceTransaction);
        Transaction otherBalanceTransaction = new Transaction("b", someFruit, 20);
        handler.apply(otherBalanceTransaction);
        Integer expected = 20;
        Integer actual = Storage.storage.get(someFruit);
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
