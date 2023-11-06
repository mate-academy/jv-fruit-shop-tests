package core.basesyntax.transactions.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.transactions.OperationHandler;
import org.junit.AfterClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private static final String FRUIT = "apple";
    private static final Integer QUANTITY = 10;
    private static final Integer PUT_STORAGE = 10;
    private static FruitTransaction transaction;
    private static Integer balanceStorage;
    private OperationHandler supplyHandler;

    @BeforeEach
    void setUp() {
        supplyHandler = new BalanceOperationHandler();
        transaction = new FruitTransaction(Operation.PURCHASE, FRUIT, QUANTITY);
        Storage.STORAGE_VALUE.put(FRUIT, PUT_STORAGE);
        balanceStorage = Storage.STORAGE_VALUE.get(FRUIT);
    }

    @Test
    void testProcessTransaction_ValidTransaction_SuccessfulTransaction() {
        boolean result = supplyHandler.processTransaction(transaction);

        assertTrue(result);
        assertEquals(QUANTITY, balanceStorage);
    }

    @Test
    void testProcessTransaction_InvalidTransactionFruit_NotOK() {
        transaction = new FruitTransaction(Operation.BALANCE, null, QUANTITY);

        assertThrows(IllegalArgumentException.class, () ->
                supplyHandler.processTransaction(transaction));
    }

    @Test
    void processTransaction_invalidTransactionOperation_NotOK() {
        transaction = new FruitTransaction(null, FRUIT, QUANTITY);

        assertThrows(IllegalArgumentException.class, () ->
                supplyHandler.processTransaction(transaction));
    }

    @AfterClass
    public static void afterClass() {
        Storage.STORAGE_VALUE.put(FRUIT, -PUT_STORAGE);
    }
}
