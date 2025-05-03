package core.basesyntax.tests.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.dao.FruitsDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.BalanceHandler;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BalanceHandlerTest {
    private OperationHandler operationHandler;
    private FruitsDao fruitsDao;
    private FruitTransaction fruitTransaction;

    @Before
    public void setUp() throws Exception {
        fruitTransaction = new FruitTransaction();
        fruitsDao = new FruitDaoImpl();
        operationHandler = new BalanceHandler(fruitsDao);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(10);
    }

    @Test (expected = RuntimeException.class)
    public void balanceHandler_NullData_NotOk() {
        operationHandler.handle(null);
    }

    @Test
    public void balanceHandler_CorrectData_Ok() {
        operationHandler.handle(fruitTransaction);
        int expected = 10;
        int actual = Storage.fruitsStorage.get(fruitTransaction.getFruit());
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruitsStorage.clear();
    }
}
