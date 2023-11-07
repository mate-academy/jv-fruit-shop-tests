package core.basesyntax.transactions.impl;

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

class PurchaseOperationHandlerTest {
    private static final String FRUIT = "apple";
    private static final Integer QUANTITY = 10;
    private static final Integer PUT_STORAGE = 11;
    private static Integer balanceStorage;
    private static FruitTransaction transaction;
    private static OperationHandler handler;

    @BeforeAll
    static void beforeAll() {
        handler = new PurchaseOperationHandler();
    }

    @BeforeEach
    void setUp() {
        transaction = new FruitTransaction(Operation.PURCHASE, FRUIT, QUANTITY);
        Storage.STORAGE_VALUE.put(FRUIT, PUT_STORAGE);
        balanceStorage = Storage.STORAGE_VALUE.get(FRUIT);
    }

    @Test
    void testProcessTransaction_transactionCorrectWork_OK() {
        boolean result = handler.processTransaction(transaction);

        assertTrue(result);
    }

    @Test
    void testProcessTransaction_whenBalanceStorageLessThanIs_NotOK() {
        Integer exceedsBalance = balanceStorage + QUANTITY;
        transaction = new FruitTransaction(Operation.PURCHASE, FRUIT, exceedsBalance);

        assertThrows(IllegalArgumentException.class, () ->
                handler.processTransaction(transaction));
    }

    @Test
    void testProcessTransaction_transactionWithNullFruit_NotOK() {
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE, null, QUANTITY);

        assertThrows(IllegalArgumentException.class, () ->
                handler.processTransaction(transaction));
    }

    @Test
    void testProcessTransaction_transactionWithNullTransaction_NotOK() {
        assertThrows(IllegalArgumentException.class, () ->
                handler.processTransaction(null));
    }

    @AfterEach
    void afterEach() {
        Storage.STORAGE_VALUE.clear();
    }
}
