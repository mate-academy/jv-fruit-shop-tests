package strategy;

import db.FruitsDao;
import db.GenericDao;
import models.Fruit;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static final GenericDao<Fruit, Integer> fruitsDao = new FruitsDao();
    private static final AdditionalOperationHandler additionalOperationHandler
            = new AdditionalOperationHandler(fruitsDao);
    private static final Fruit izir = new Fruit("inzir");

    @Test(expected = RuntimeException.class)
    public void changeBalance_NotOk() {
        additionalOperationHandler.changeBalance(izir.getName(), -100);
    }
}
