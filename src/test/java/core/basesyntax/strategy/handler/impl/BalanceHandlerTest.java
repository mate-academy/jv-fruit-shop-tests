package core.basesyntax.strategy.handler.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.handler.OperationHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class BalanceHandlerTest {
    private static final String APPLE = "apple";
    public static final int VALID_FRUIT_QUANTITY = 5;
    private static final String BANANA = "banana";
    private static final int INVALID_FRUIT_QUANTITY = -5;
    private static final int ZERO_FRUIT_QUANTITY = 0;
    private static OperationHandler handler;

    @BeforeClass
    public static void beforeClass() {
        handler = new BalanceHandler();
    }

    @Before
    public void setUp() {
        Storage.storage.clear();
    }

    @After
    public void afterEach() {
        Storage.storage.clear();
    }

    @Test
    public void test_addFruitToStorage_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                APPLE,
                VALID_FRUIT_QUANTITY);
        handler.handle(transaction);
        Integer appleQuantity = Storage.storage.get(APPLE);
        assertNotNull(appleQuantity);
        assertEquals(VALID_FRUIT_QUANTITY, (int) appleQuantity);
    }

    @Test
    public void test_add_fruit_with_zero_quantity_to_storage() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                APPLE,
                ZERO_FRUIT_QUANTITY);
        handler.handle(transaction);
        assertNull(Storage.storage.get(BANANA));
    }

    @Test
    public void test_negativeQuantity_not_added_to_storage() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                BANANA,
                INVALID_FRUIT_QUANTITY);
        handler.handle(transaction);
        assertNull(Storage.storage.get(BANANA));
    }
}