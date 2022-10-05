package core.basesyntax.tests.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.dao.FruitsDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.ReturnHandler;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReturnHandlerTest {
    private OperationHandler operationHandler;
    private FruitTransaction fruitTransaction;
    private FruitsDao fruitsDao;

    @Before
    public void setUp() throws Exception {
        fruitsDao = new FruitDaoImpl();
        fruitTransaction = new FruitTransaction();
        operationHandler = new ReturnHandler(fruitsDao);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(10);
    }

    @Test
    public void returnHandler_CorrectData_Ok() {
        fruitsDao.addFruit("banana", 0);
        operationHandler.handle(fruitTransaction);
        int expected = 10;
        assertEquals(expected,fruitsDao.getQuantityByFruit(fruitTransaction.getFruit()));
    }

    @Test
    public void returnHandler_CorrectDataFruitDoNotExist_Ok() {
        operationHandler.handle(fruitTransaction);
        assertEquals(10, fruitsDao.getQuantityByFruit(fruitTransaction.getFruit()));
    }

    @Test (expected = RuntimeException.class)
    public void returnHandler_NullData_NotOk() {
        operationHandler.handle(null);
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruitsStorage.clear();
    }
}
