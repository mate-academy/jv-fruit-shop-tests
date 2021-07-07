package strategy;

import db.FruitsDao;
import db.GenericDao;
import models.Fruit;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class AdditionalOperationHandlerTest {
    private static final GenericDao<Fruit, Integer> fruitsDao = new FruitsDao();
    private static final AdditionalOperationHandler additionalOperationHandler
            = new AdditionalOperationHandler(fruitsDao);
    private static final Fruit izir = new Fruit("inzir");

    @Test
    public void changeBalance_Ok() {
        additionalOperationHandler.changeBalance(izir.getName(), 1);
        additionalOperationHandler.changeBalance(izir.getName(), 1);
        additionalOperationHandler.changeBalance(izir.getName(), 1);
        int result = fruitsDao.get(izir);
        assertEquals(3, result);
    }
}