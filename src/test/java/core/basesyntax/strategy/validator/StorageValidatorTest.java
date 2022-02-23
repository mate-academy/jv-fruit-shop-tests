package core.basesyntax.strategy.validator;

import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.FruitModel;
import core.basesyntax.strategy.handlers.PurchaseOperationHandler;
import org.junit.Test;

public class StorageValidatorTest {
    /*
    @Test(expected = RuntimeException.class)
    public void fruitDoesNotExists_Exception() {
        StorageValidator storageValidator = new PurchaseOperationHandler();
        StorageDaoImpl storageDao = new StorageDaoImpl();
        FruitModel fruitModel = new FruitModel("apple", 10);
        storageDao.putFruitModel(fruitModel);
        storageValidator.doesStorageContainsFruit(new FruitModel("banana", 10), "p");
    }

     */

    @Test
    public void checkPassedSuccessfully_True() {
        StorageValidator storageValidator = new PurchaseOperationHandler();
        StorageDaoImpl storageDao = new StorageDaoImpl();
        FruitModel fruitModel = new FruitModel("apple", 10);
        storageDao.putFruitModel(fruitModel);
        assertTrue(storageValidator
                .doesStorageContainsFruit(new FruitModel("apple", 10), "p"));
    }
}
