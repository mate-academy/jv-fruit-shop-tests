package core.basesyntax.strategy.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    @Test
    public void returnExistingFruit_Ok() {
        Fruit someFruit = new Fruit("someFruit");
        Storage.storage.put(someFruit, 10);
        Transaction returnTransaction = new Transaction("r", someFruit, 10);
        new ReturnOperationHandler().apply(returnTransaction);
        Integer expected = 20;
        Integer actual = Storage.storage.get(someFruit);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void returnNotExistingFruit_Not_Ok() {
        Fruit someFruit = new Fruit("someFruit");
        Transaction returnTransaction = new Transaction("r", someFruit, 10);
        new ReturnOperationHandler().apply(returnTransaction);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
