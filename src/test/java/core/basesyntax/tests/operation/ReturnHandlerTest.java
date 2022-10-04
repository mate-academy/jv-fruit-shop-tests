package core.basesyntax.tests.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.dao.FruitsDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.ReturnHandler;
import core.basesyntax.storage.Storage;
import org.junit.Before;
import org.junit.Test;

public class ReturnHandlerTest {
    private OperationHandler operationHandler;
    private FruitTransaction banana = new FruitTransaction();

    @Before
    public void setUp() throws Exception {
        banana.setFruit("banana");
        banana.setQuantity(10);
        Storage.fruitsStorage.clear();
    }

    @Test
    public void returnHandler_CorrectData_Ok() {
        FruitsDao fruitsDao = new FruitDaoImpl();
        fruitsDao.addFruit("banana", 0);
        operationHandler = new ReturnHandler(fruitsDao);
        operationHandler.handle(banana);
        int expected = 10;
        assertEquals(expected,fruitsDao.getQuantityByFruit(banana.getFruit()));
    }

    @Test
    public void returnHandler_CorrectDataFruitDontExist_Ok() {
        FruitsDao fruitsDao = new FruitDaoImpl();
        operationHandler = new ReturnHandler(fruitsDao);
        operationHandler.handle(banana);
        int expected = 10;
        assertEquals(expected,fruitsDao.getQuantityByFruit(banana.getFruit()));
    }

    @Test (expected = RuntimeException.class)
    public void returnHandler_NullData_NotOk() {
        FruitsDao fruitsDao = new FruitDaoImpl();
        operationHandler = new ReturnHandler(fruitsDao);
        operationHandler.handle(null);
    }
}
