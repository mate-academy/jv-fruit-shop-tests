package core.basesyntax.operation.handler;

import static org.junit.Assert.assertEquals;

import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BalanceOperationTest {
    private static final String FRUIT = "kiwi";
    private BalanceOperation balanceOperation;

    @Before
    public void setUp() {
        Storage.storage.clear();
        balanceOperation = new BalanceOperation();
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test
    public void handle_balance_shouldSetQuantity() {
        balanceOperation.handle(FRUIT, 25);

        int expected = 25;
        int actual = Storage.storage.get(FRUIT);
        assertEquals(expected, actual);
    }
}

