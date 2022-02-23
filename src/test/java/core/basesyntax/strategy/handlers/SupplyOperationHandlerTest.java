package core.basesyntax.strategy.handlers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.FruitModel;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    @Test
    public void doOperation_True() {
        SupplyOperationHandler supplyOperationHandler = new SupplyOperationHandler();
        StorageDaoImpl storageDao = new StorageDaoImpl();
        FruitModel fruitModel = new FruitModel("apple", 10);
        storageDao.putFruitModel(fruitModel);
        assertTrue(supplyOperationHandler.doOperation(fruitModel));
        assertTrue(storageDao.containsKey(fruitModel.getName()));
        assertEquals(storageDao.getAmount(fruitModel.getName()), 20);
    }
}
