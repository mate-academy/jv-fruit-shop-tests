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

public class SupplyOperationServiceTest {
    private StorageDao storageDao;
    private OperationService operationService;

    @Before
    public void setUp() {
        storageDao = new StorageDaoImpl();
        operationService = new SupplyOperationService(storageDao);
    }

    @Test
    public void process_supplyOperationServiceValidData_Ok() {
        Fruit fruit = new Fruit("Banana");
        FruitTransaction fruitTransaction = new FruitTransaction();

        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransaction.setFruit(fruit);
        fruitTransaction.setQuantity(10);

        operationService.process(fruitTransaction);

        int actual = Storage.fruitStorage.get(fruit);

        Assert.assertEquals(10, actual);
    }

    @After
    public void tearDown() {
        Storage.fruitStorage.clear();
    }
}
