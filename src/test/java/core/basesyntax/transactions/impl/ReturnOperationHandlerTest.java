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

public class ReturnOperationHandlerTest {
    private static final String FRUIT = "apple";
    private static final int QUANTITY = 100;
    private static final int INITIAL_BALANCE = 50;
    private static OperationHandler handler;
    private FruitTransaction transaction;

    @BeforeAll
    static void beforeAll() {
        handler = new ReturnOperationHandler();
    }

    @BeforeEach
    void setUp() {
        transaction = new FruitTransaction(Operation.PURCHASE, FRUIT, QUANTITY);
        Storage.STORAGE_VALUE.put(FRUIT, INITIAL_BALANCE);
    }

    @Test
    void testProcessTransaction_returnPositiveQuantity_OK() {
        boolean result = handler.processTransaction(transaction);

        int updatedBalance = Storage.STORAGE_VALUE.get(FRUIT);

        assertTrue(result);
        assertEquals(INITIAL_BALANCE + QUANTITY, updatedBalance);
    }

    @Test
    void testProcessTransaction_returnNegativeQuantity_NotOK() {
        int negativeQuantity = -10;
        transaction = new FruitTransaction(Operation.RETURN, FRUIT, negativeQuantity);

        assertThrows(IllegalArgumentException.class, () ->
                handler.processTransaction(transaction));
    }

    @Test
    void testProcessTransaction_returnNullTransaction_NotOK() {
        assertThrows(IllegalArgumentException.class, () ->
                handler.processTransaction(null));
    }

    @AfterEach
    void afterEach() {
        Storage.STORAGE_VALUE.clear();
    }
}
