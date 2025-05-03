package core.basesyntax.tests.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.dao.FruitsDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.PurchaseHandler;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PurchaseHandlerTest {
    private OperationHandler operationHandler;
    private FruitsDao fruitsDao;
    private FruitTransaction fruitTransaction;

    @Before
    public void setUp() throws Exception {
        fruitTransaction = new FruitTransaction();
        fruitsDao = new FruitDaoImpl();
        operationHandler = new PurchaseHandler(fruitsDao);
        Storage.fruitsStorage.put("banana", 20);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(10);
    }

    @Test
    public void purchaseHandler_CorrectData_Ok() {
        operationHandler.handle(fruitTransaction);
        int expected = 10;
        int actual = Storage.fruitsStorage.get(fruitTransaction.getFruit());
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void purchaseHandler_NullData_NotOk() {
        operationHandler.handle(null);
    }

    @Test (expected = RuntimeException.class)
    public void purchaseHandler_FruitsDaoDoNotContainEnough_NotOk() {
        fruitTransaction.setQuantity(30);
        operationHandler.handle(fruitTransaction);
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruitsStorage.clear();
    }
}
