package core.basesyntax.strategy.handlers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.FruitModel;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static PurchaseOperationHandler purchaseOperationHandler;

    @Before
    public void setUp() throws Exception {
        purchaseOperationHandler = new PurchaseOperationHandler();
    }

    @Test(expected = RuntimeException.class)
    public void doOperation_negativeResult_Exception() {
        StorageDaoImpl storageDao = new StorageDaoImpl();
        FruitModel fruitModel = new FruitModel("apple", 10);
        storageDao.putFruitModel(fruitModel);
        purchaseOperationHandler.doOperation(new FruitModel("apple", 20));
    }

    @Test(expected = RuntimeException.class)
    public void doOperation_fruitIsAlreadyExists_Exception() {
        StorageDaoImpl storageDao = new StorageDaoImpl();
        FruitModel fruitModel = new FruitModel("apple", 10);
        storageDao.putFruitModel(fruitModel);
        purchaseOperationHandler.doOperation(new FruitModel("apple", 20));
    }

    @Test
    public void doOperation_operationDoneCorrectly_True() {
        StorageDaoImpl storageDao = new StorageDaoImpl();
        FruitModel fruitModel = new FruitModel("apple", 10);
        storageDao.putFruitModel(fruitModel);
        assertTrue(purchaseOperationHandler.doOperation(new FruitModel("apple", 5)));
        assertTrue(storageDao.containsKey(fruitModel.getName()));
        assertEquals(storageDao.getAmount(fruitModel.getName()), 5);
    }
}
