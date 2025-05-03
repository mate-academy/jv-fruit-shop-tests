package core.basesyntax.strategy.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    @Test
    public void supplyExistingFruit_Ok() {
        Fruit someFruit = new Fruit("someFruit");
        Storage.storage.put(someFruit, 10);
        Transaction supplyTransaction = new Transaction("s", someFruit, 10);
        new SupplyOperationHandler().apply(supplyTransaction);
        Integer expected = 20;
        Integer actual = Storage.storage.get(someFruit);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void supplyNotExistingFruit_Not_Ok() {
        Fruit someFruit = new Fruit("someFruit");
        Transaction supplyTransaction = new Transaction("s", someFruit, 10);
        new SupplyOperationHandler().apply(supplyTransaction);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}

