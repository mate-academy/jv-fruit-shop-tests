package core.basesyntax.strategy;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class PurchaseOperationServiceTest {
    @Test
    public void process_purchaseOperation_ok() {
        Fruit fruit = new Fruit("Banana");
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit(fruit);
        fruitTransaction.setQuantity(10);
        StorageDao storageDao = new StorageDaoImpl();
        Storage.fruitStorage.put(fruit,20);

        OperationService operationService = new PurchaseOperationService(storageDao);
        operationService.process(fruitTransaction);

        Assert.assertTrue(Storage.fruitStorage.containsKey(fruit));
        Assert.assertTrue(Storage.fruitStorage.containsValue(10));
    }

    @Test(expected = RuntimeException.class)
    public void process_purchaseMoreThanInStorageOperation_ThrowException() {
        Fruit fruit = new Fruit("Banana");
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit(fruit);
        fruitTransaction.setQuantity(50);
        StorageDao storageDao = new StorageDaoImpl();
        Storage.fruitStorage.put(fruit,10);

        OperationService operationService = new PurchaseOperationService(storageDao);
        operationService.process(fruitTransaction);
    }

    @After
    public void tearDown() {
        Storage.fruitStorage.clear();
    }
}
