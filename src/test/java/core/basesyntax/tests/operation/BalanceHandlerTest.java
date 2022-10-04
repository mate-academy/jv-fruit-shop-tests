package core.basesyntax.tests.operation;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.dao.FruitsDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.BalanceHandler;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.storage.Storage;
import org.junit.Before;
import org.junit.Test;

public class BalanceHandlerTest {
    private OperationHandler operationHandler;
    private FruitTransaction banana = new FruitTransaction();

    @Before
    public void setUp() throws Exception {
        banana.setFruit("banana");
        banana.setQuantity(10);
        Storage.fruitsStorage.clear();
    }

    @Test (expected = RuntimeException.class)
    public void balanceHandler_NullData_NotOk() {
        FruitsDao fruitsDao = new FruitDaoImpl();
        operationHandler = new BalanceHandler(fruitsDao);
        operationHandler.handle(null);
    }
}
