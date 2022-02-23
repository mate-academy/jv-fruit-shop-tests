package core.basesyntax.strategy.handlers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.FruitModel;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    @Test
    public void doOperation_returnFruit_Ok() {
        ReturnOperationHandler returnOperationHandler = new ReturnOperationHandler();
        StorageDaoImpl storageDao = new StorageDaoImpl();
        FruitModel fruitModel = new FruitModel("apple", 10);
        storageDao.putFruitModel(fruitModel);
        assertTrue(returnOperationHandler.doOperation(fruitModel));
        assertTrue(storageDao.containsKey(fruitModel.getName()));
        assertEquals(storageDao.getAmount(fruitModel.getName()), 20);
    }
}
