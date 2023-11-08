package core.basesyntax.transactions.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyOperationHandlerTest {
    private static final String FRUIT = "apple";
    private static final Integer QUANTITY = 10;
    private static final Integer PUT_STORAGE = 10;
    private static Integer sumBalanceQuantity;
    private static int balanceStorage;
    private static SupplyOperationHandler handler;
    private static FruitTransaction transaction;

    @BeforeAll
    static void beforeAll() {
        handler = new SupplyOperationHandler();
        transaction = new FruitTransaction(Operation.SUPPLY, FRUIT, QUANTITY);
    }

    @BeforeEach
    void setUp() {
        Storage.STORAGE_VALUE.put(FRUIT, PUT_STORAGE);
        balanceStorage = Storage.STORAGE_VALUE.get(FRUIT);
        sumBalanceQuantity = balanceStorage + QUANTITY;
    }

    @Test
    void testProcess_transactionAddsToFruitBalance_OK() {
        boolean result = handler.processTransaction(transaction);
        assertTrue(result);
        assertEquals(sumBalanceQuantity, Storage.STORAGE_VALUE.get(FRUIT));
        assertTrue(handler.processTransaction(transaction));
    }

    @Test
    void testProcess_transactionWithNegativeQuantity_NotOK() {
        int negativeQuantity = -10;
        transaction = new FruitTransaction(Operation.SUPPLY, FRUIT, negativeQuantity);
        assertThrows(IllegalArgumentException.class, () ->
                handler.processTransaction(transaction));
    }

    @Test
    void testProcess_transactionWithNullFruit_NotOK() {
        transaction = new FruitTransaction(Operation.SUPPLY, null, QUANTITY);
        assertThrows(IllegalArgumentException.class, () ->
                handler.processTransaction(transaction));
    }

    @Test
    void testProcess_transactionWithNullTransaction_NotOK() {
        assertThrows(IllegalArgumentException.class, () ->
                handler.processTransaction(null));
    }

    @AfterEach
    void afterEach() {
        Storage.STORAGE_VALUE.clear();
    }
}
