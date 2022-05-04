
package core.basesyntax.strategy;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class ReturnOperationServiceTest {
    @Test
    public void process_returnOperationServiceValidData_Ok() {
        Fruit fruit = new Fruit("Banana");
        FruitTransaction fruitTransaction = new FruitTransaction();
        StorageDao storageDao = new StorageDaoImpl();

        fruitTransaction.setOperation(FruitTransaction.Operation.RETURN);
        fruitTransaction.setFruit(fruit);
        fruitTransaction.setQuantity(10);

        OperationService operationService = new ReturnOperationService(storageDao);

        operationService.process(fruitTransaction);

        Assert.assertTrue(Storage.fruitStorage.containsKey(fruit));
        Assert.assertTrue(Storage.fruitStorage.containsValue(10));
    }

    @After
    public void tearDown() {
        Storage.fruitStorage.clear();
    }
}
