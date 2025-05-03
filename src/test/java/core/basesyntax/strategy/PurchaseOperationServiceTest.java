package core.basesyntax.strategy;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationServiceTest {
    private FruitTransaction fruitTransaction;
    private StorageDao storageDao;
    private OperationService operationService;

    @Before
    public void setUp() {
        fruitTransaction = new FruitTransaction();
        storageDao = new StorageDaoImpl();
        operationService = new PurchaseOperationService(storageDao);
    }

    @Test
    public void process_purchaseOperationServiceValidData_Ok() {
        Fruit fruit = new Fruit("Banana");

        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit(fruit);
        fruitTransaction.setQuantity(10);

        Storage.fruitStorage.put(fruit,20);

        operationService.process(fruitTransaction);

        int actual = Storage.fruitStorage.get(fruit);

        Assert.assertEquals(10, actual);
    }

    @Test(expected = RuntimeException.class)
    public void process_purchaseMoreThanInStorage_ExceptionThrown() {
        Fruit fruit = new Fruit("Banana");
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit(fruit);
        fruitTransaction.setQuantity(50);
        Storage.fruitStorage.put(fruit,10);

        operationService.process(fruitTransaction);
    }

    @After
    public void tearDown() {
        Storage.fruitStorage.clear();
    }
}
