package core.basesyntax.strategy.handler;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new BalanceOperationHandler();
    }

    @Test
    public void operationHandler_balance_ok() {
        Fruit banana = new Fruit("banana");
        Transaction transaction = new Transaction(Operation.BALANCE, banana, 20);
        operationHandler.apply(transaction);
        assertEquals(Integer.valueOf(20), Storage.storage.get(banana));
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
