package core.basesyntax.tests.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.dao.FruitsDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.PurchaseHandler;
import core.basesyntax.storage.Storage;
import org.junit.Before;
import org.junit.Test;

public class PurchaseHandlerTest {
    private OperationHandler operationHandler;
    private FruitTransaction banana = new FruitTransaction();

    @Before
    public void setUp() throws Exception {
        banana.setFruit("banana");
        banana.setQuantity(10);
        Storage.fruitsStorage.clear();
    }

    @Test
    public void purchaseHandler_CorrectData_Ok() {
        FruitsDao fruitsDao = new FruitDaoImpl();
        fruitsDao.addFruit("banana", 20);
        operationHandler = new PurchaseHandler(fruitsDao);
        operationHandler.handle(banana);
        int expected = 10;
        assertEquals(fruitsDao.getQuantityByFruit(banana.getFruit()), expected);
    }

    @Test (expected = RuntimeException.class)
    public void purchaseHandler_NullData_NotOk() {
        FruitsDao fruitsDao = new FruitDaoImpl();
        fruitsDao.addFruit("banana", 20);
        operationHandler = new PurchaseHandler(fruitsDao);
        operationHandler.handle(null);
    }

    @Test (expected = RuntimeException.class)
    public void purchaseHandler_FruitsDaoDontContainEnough_NotOk() {
        FruitsDao fruitsDao = new FruitDaoImpl();
        fruitsDao.addFruit("banana", 20);
        operationHandler = new PurchaseHandler(fruitsDao);
        banana.setQuantity(30);
        operationHandler.handle(banana);
    }
}
