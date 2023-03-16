package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.StorageTransaction;
import core.basesyntax.strategy.TransactionHandler;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyTransactionHandlerTest {
    private static final String FRUIT_NAME = "apple";
    private static final Integer START_QUANTITY = 100;
    private static final Integer SUPPLY_QUANTITY = 90;
    private static final StorageTransaction.Operation SUPPLY =
            StorageTransaction.Operation.SUPPLY;
    private static TransactionHandler supplyHandler;
    private static StorageTransaction transaction;

    @BeforeClass
    public static void setUp() {
        supplyHandler = new SupplyTransactionHandler();
        transaction = new StorageTransaction(SUPPLY, FRUIT_NAME, SUPPLY_QUANTITY);
        Storage.storage.put(FRUIT_NAME, START_QUANTITY);
    }

    @Test
    public void handle_changeQuantity() {
        int expected = START_QUANTITY + SUPPLY_QUANTITY;
        supplyHandler.handle(transaction);
        int actual = Storage.storage.get(FRUIT_NAME);
        assertEquals("Expected quantity: " + expected + " but was " + actual,
                expected, actual);
    }

    @After
    public void after() {
        Storage.storage.clear();
    }
}
