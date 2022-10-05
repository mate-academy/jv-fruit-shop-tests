package core.basesyntax.tests.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.dao.FruitsDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.SupplyHandler;
import core.basesyntax.storage.Storage;
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
        Storage.fruitsStorage.clear();
    }

    @Test
    public void supplyHandler_CorrectDataButFruitDoNotExist_Ok() {
        operationHandler.handle(fruitTransaction);
        assertEquals(10, fruitsDao.getQuantityByFruit(fruitTransaction.getFruit()));
    }

    @Test
    public void supplyHandler_CorrectDataFruitExist_Ok() {
        fruitsDao.addFruit("banana", 0);
        operationHandler.handle(fruitTransaction);
        assertEquals(10, fruitsDao.getQuantityByFruit(fruitTransaction.getFruit()));
    }

    @Test (expected = RuntimeException.class)
    public void supplyHandler_NullData_NotOk() {
        operationHandler.handle(null);
    }
}
