package core.basesyntax.transactions.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.transactions.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private static final String FRUIT = "apple";
    private static final Integer QUANTITY = 10;
    private static final Integer PUT_STORAGE = 10;
    private static FruitTransaction transaction;
    private static Integer balanceStorage;
    private static OperationHandler handler;

    @BeforeAll
    static void beforeAll() {
        handler = new BalanceOperationHandler();
    }

    @BeforeEach
    void setUp() {
        transaction = new FruitTransaction(Operation.PURCHASE, FRUIT, QUANTITY);
        Storage.STORAGE_VALUE.put(FRUIT, PUT_STORAGE);
        balanceStorage = Storage.STORAGE_VALUE.get(FRUIT);
    }

    @Test
    void testProcessTransaction_ValidTransaction_SuccessfulTransaction() {
        boolean result = handler.processTransaction(transaction);

        assertTrue(result);
        assertEquals(QUANTITY, balanceStorage);
    }

    @Test
    void testProcessTransaction_InvalidTransactionFruit_NotOK() {
        transaction = new FruitTransaction(Operation.BALANCE, null, QUANTITY);

        assertThrows(IllegalArgumentException.class, () ->
                handler.processTransaction(transaction));
    }

    @Test
    void processTransaction_invalidTransactionOperation_NotOK() {
        transaction = new FruitTransaction(null, FRUIT, QUANTITY);

        assertThrows(IllegalArgumentException.class, () ->
                handler.processTransaction(transaction));
    }

    @AfterEach
    void afterEach() {
        Storage.STORAGE_VALUE.clear();
    }
}
