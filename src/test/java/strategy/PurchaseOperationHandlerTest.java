package strategy;

import static org.junit.Assert.assertEquals;

import db.FruitsDao;
import db.GenericDao;
import db.Storage;
import models.Fruit;
import org.junit.After;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static final GenericDao<Fruit, Integer> fruitsDao = new FruitsDao();
    private static final OperationHandler additionalOperationHandler
            = new AdditionalOperationHandler(fruitsDao);
    private static final Fruit izir = new Fruit("inzir");

    @After
    public void tearDownStorage() {
        Storage.fruits.clear();
    }

    @Test
    public void changeBalance_Ok() {
        additionalOperationHandler.changeBalance(izir.getName(), 100);
        int result = fruitsDao.get(izir);
        assertEquals(100, result);
    }

    @Test(expected = RuntimeException.class)
    public void changeBalance_NotOk() {
        additionalOperationHandler.changeBalance(izir.getName(), -100);
    }
}
