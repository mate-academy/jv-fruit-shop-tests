package core.basesyntax.tests.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.dao.FruitsDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.SupplyHandler;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SupplyHandlerTest {
    private OperationHandler operationHandler;
    private FruitTransaction fruitTransaction;
    private FruitsDao fruitsDao;

    @Before
    public void setUp() throws Exception {
        fruitsDao = new FruitDaoImpl();
        fruitTransaction = new FruitTransaction();
        operationHandler = new SupplyHandler(fruitsDao);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(10);
    }

    @Test
    public void supplyHandler_CorrectDataButFruitDoNotExist_Ok() {
        operationHandler.handle(fruitTransaction);
        int expected = 10;
        int actual = Storage.fruitsStorage.get(fruitTransaction.getFruit());
        assertEquals(expected, actual);
    }

    @Test
    public void supplyHandler_CorrectDataFruitExist_Ok() {
        Storage.fruitsStorage.put("banana", 0);
        operationHandler.handle(fruitTransaction);
        int expected = 10;
        int actual = Storage.fruitsStorage.get(fruitTransaction.getFruit());
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void supplyHandler_NullData_NotOk() {
        operationHandler.handle(null);
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruitsStorage.clear();
    }
}
