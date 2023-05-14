package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static SupplyOperationHandler supplyOperationHandler;
    private static final String NAME_FRUIT = "banana";
    private static final String OPERATION = "s";

    @Before
    public void setUp() {
        supplyOperationHandler = new SupplyOperationHandler();
    }

    @After
    public void afterEachTest() {
        Storage.storage.clear();
    }

    @Test
    public void handle_purchaseIsMoreThenBalance_ok() {
        Storage.storage.put(NAME_FRUIT, 80);
        FruitTransaction fruitTransaction = new FruitTransaction(OPERATION, NAME_FRUIT, 20);
        supplyOperationHandler.handle(fruitTransaction);
        int expected = 100;
        int actual = Storage.storage.get(NAME_FRUIT);
        assertEquals(expected, actual);
    }
}
