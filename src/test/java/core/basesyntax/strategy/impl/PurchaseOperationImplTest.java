package core.basesyntax.strategy.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.StrategyOperation;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationImplTest {
    private static StorageDao storageDao;
    private static StrategyOperation strategyOperation;

    @BeforeClass
    public static void beforeClass() {
        storageDao = new StorageDaoImpl();
        strategyOperation = new PurchaseOperationImpl(storageDao);
        Storage.storage.put(new Fruit("banana"), 20);
    }

    @Test
    public void handle_purchaseOperation_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit(new Fruit("banana"));
        fruitTransaction.setQuantity(10);

        strategyOperation.handle(fruitTransaction);

        int actualQuantity = Storage.storage.get(fruitTransaction.getFruit());
        int expectedQuantity = 10;

        Assert.assertEquals(expectedQuantity, actualQuantity);
        Assert.assertTrue(Storage.storage.containsKey(fruitTransaction.getFruit()));
        Assert.assertEquals(1, Storage.storage.size());
    }

    @Test (expected = RuntimeException.class)
    public void handle_purchaseOperationWithNotEnoughFruits_NotOk() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit(new Fruit("banana"));
        fruitTransaction.setQuantity(30);

        strategyOperation.handle(fruitTransaction);
    }

    @After
    public void afterEach() {
        Storage.storage.clear();
    }
}
