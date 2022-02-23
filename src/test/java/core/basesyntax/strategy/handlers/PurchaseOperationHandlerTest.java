package core.basesyntax.strategy.handlers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.FruitModel;
//import core.basesyntax.strategy.validator.StorageValidator;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    @Test(expected = RuntimeException.class)
    public void negativeResult_Exception() {
        PurchaseOperationHandler purchaseOperationHandler = new PurchaseOperationHandler();
        StorageDaoImpl storageDao = new StorageDaoImpl();
        FruitModel fruitModel = new FruitModel("apple", 10);
        storageDao.putFruitModel(fruitModel);
        purchaseOperationHandler.doOperation(new FruitModel("apple", 20));
    }

    @Test(expected = RuntimeException.class)
    public void fruitIsAlreadyExists_Exception() {
        PurchaseOperationHandler purchaseOperationHandler = new PurchaseOperationHandler();
        StorageDaoImpl storageDao = new StorageDaoImpl();
        FruitModel fruitModel = new FruitModel("apple", 10);
        storageDao.putFruitModel(fruitModel);
        purchaseOperationHandler.doOperation(new FruitModel("apple", 20));
    }

    @Test
    public void fruitIsAdded_True() {
        PurchaseOperationHandler purchaseOperationHandler = new PurchaseOperationHandler();
        StorageDaoImpl storageDao = new StorageDaoImpl();
        FruitModel fruitModel = new FruitModel("apple", 10);
        storageDao.putFruitModel(fruitModel);
        assertTrue(purchaseOperationHandler.doOperation(new FruitModel("apple", 5)));
        assertTrue(storageDao.containsKey(fruitModel.getName()));
        assertEquals(storageDao.getAmount(fruitModel.getName()), 5);
    }
}
