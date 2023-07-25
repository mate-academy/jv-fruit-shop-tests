package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.strategy.handlers.BalanceHandler;
import core.basesyntax.strategy.handlers.OperationHandler;
import org.junit.Before;
import org.junit.Test;

public class BalanceHandlerTest {
    private OperationHandler balanceHandler;

    @Before
    public void setUp() {
        balanceHandler = new BalanceHandler();
    }

    @Test
    public void balance_ValidData_Ok() {
        balanceHandler.doOperation("apple", 10);
        assertTrue(Storage.storageMap.containsKey("apple"));
        assertEquals(10, (int) Storage.storageMap.get("apple"));
    }
}
