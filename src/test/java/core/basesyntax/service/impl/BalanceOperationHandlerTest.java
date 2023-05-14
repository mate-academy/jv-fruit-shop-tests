package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static BalanceOperationHandler balanceOperationHandler;
    private static final String NAME_FRUIT = "banana";
    private static final String OPERATION = "b";

    @Before
    public void setUp() {
        balanceOperationHandler = new BalanceOperationHandler();
    }

    @After
    public void afterEachTest() {
        Storage.storage.clear();
    }

    @Test
    public void handle_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(OPERATION, NAME_FRUIT, 60);
        balanceOperationHandler.handle(fruitTransaction);
        int expected = 60;
        int actual = Storage.storage.get(NAME_FRUIT);
        assertEquals(expected, actual);
    }
}
