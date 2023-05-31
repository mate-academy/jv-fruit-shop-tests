package core.basesyntax.strategy.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    @Test
    public void purchaseExistingFruit_Ok() {
        Fruit someFruit = new Fruit("someFruit");
        Storage.storage.put(someFruit, 20);
        Transaction purchaseTransaction = new Transaction("p", someFruit, 10);
        new PurchaseOperationHandler().apply(purchaseTransaction);
        Integer expected = 10;
        Integer actual = Storage.storage.get(someFruit);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseNotExistingFruit_Not_Ok() {
        Fruit someFruit = new Fruit("someFruit");
        Transaction purchaseTransaction = new Transaction("p", someFruit, 10);
        new PurchaseOperationHandler().apply(purchaseTransaction);
    }

    @Test
    public void purchaseLastFruit_Ok() {
        Fruit someFruit = new Fruit("someFruit");
        Storage.storage.put(someFruit, 10);
        Transaction purchaseTransaction = new Transaction("p", someFruit, 10);
        new PurchaseOperationHandler().apply(purchaseTransaction);
        Integer expected = 0;
        Integer actual = Storage.storage.get(someFruit);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseNotEnoughQuantityFruit_Not_Ok() {
        Fruit someFruit = new Fruit("someFruit");
        Storage.storage.put(someFruit, 10);
        Transaction purchaseTransaction = new Transaction("p", someFruit, 100);
        new PurchaseOperationHandler().apply(purchaseTransaction);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
