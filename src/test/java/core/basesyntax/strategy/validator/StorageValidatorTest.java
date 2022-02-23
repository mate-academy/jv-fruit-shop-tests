package core.basesyntax.strategy.validator;

import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.FruitModel;
import core.basesyntax.strategy.handlers.PurchaseOperationHandler;
import org.junit.Test;

public class StorageValidatorTest {

    @Test
    public void putFruitModel_checkPassedSuccessfully_Ok() {
        StorageValidator storageValidator = new PurchaseOperationHandler();
        StorageDaoImpl storageDao = new StorageDaoImpl();
        FruitModel fruitModel = new FruitModel("apple", 10);
        storageDao.putFruitModel(fruitModel);
        assertTrue(storageValidator
                .doesStorageContainsFruit(new FruitModel("apple", 10), "p"));
    }
}
