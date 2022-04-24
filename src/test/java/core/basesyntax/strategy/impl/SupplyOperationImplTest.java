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

public class SupplyOperationImplTest {
    private static StorageDao storageDao;
    private static StrategyOperation strategyOperation;

    @BeforeClass
    public static void beforeClass() {
        storageDao = new StorageDaoImpl();
        strategyOperation = new BalanceOperationImpl(storageDao);
    }

    @Test
    public void handle_Supply_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransaction.setFruit(new Fruit("banana"));
        fruitTransaction.setQuantity(10);

        strategyOperation.handle(fruitTransaction);

        int actualQuantity = Storage.storage.get(fruitTransaction.getFruit());
        int expectedQuantity = fruitTransaction.getQuantity();

        Assert.assertEquals(expectedQuantity, actualQuantity);
        Assert.assertTrue(Storage.storage.containsKey(fruitTransaction.getFruit()));
        Assert.assertEquals(1, Storage.storage.size());
    }

    @After
    public void afterEach() {
        Storage.storage.clear();
    }
}
