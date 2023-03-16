package core.basesyntax.strategy.handler.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.handler.OperationHandler;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseHandlerTest {

    private static final String APPLE = "apple";
    private static final int VALID_FRUIT_QUANTITY = 10;
    private static final int ANOTHER_VALID_FRUIT_QUANTITY = 5;
    private static final int VALID_OPERATION_QUANTITY = 5;
    private static final int MORE_THAN_VALID_FRUIT_QUANTITY = 11;
    private static final String BANANA = "banana";
    private static final int ZERO_FRUIT_QUANTITY = 0;
    private static final int INVALID_OPERATION_QUANTITY = -7;
    private static OperationHandler handler;

    @BeforeClass
    public static void beforeClass() {
        handler = new PurchaseHandler();
    }

    @Before
    public void setUp() {
        Storage.storage.clear();
        Storage.storage.put(APPLE, VALID_FRUIT_QUANTITY);
        Storage.storage.put(BANANA, ANOTHER_VALID_FRUIT_QUANTITY);
    }

    @Test
    public void test_Update_FruitStorage_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                APPLE,
                ANOTHER_VALID_FRUIT_QUANTITY);
        Integer beforeQuantity = Storage.storage.getOrDefault(APPLE, ZERO_FRUIT_QUANTITY);
        handler.handle(transaction);
        Integer actualQuantity = Storage.storage.getOrDefault(APPLE, ZERO_FRUIT_QUANTITY);
        Integer expectedQuantity = beforeQuantity - ANOTHER_VALID_FRUIT_QUANTITY;
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test(expected = RuntimeException.class)
    public void test_purchase_quantity_is_greater_than_current() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                APPLE,
                MORE_THAN_VALID_FRUIT_QUANTITY);
        handler.handle(transaction);
    }

    @Test
    public void test_Reduce_Fruit_Quantity_In_Storage() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                APPLE,
                VALID_OPERATION_QUANTITY);
        handler.handle(transaction);
        Integer actualQuantity = Storage.storage.get(APPLE);
        Integer expectedQuantity = VALID_FRUIT_QUANTITY - VALID_OPERATION_QUANTITY;
        assertEquals(expectedQuantity, actualQuantity);

        transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                APPLE,
                VALID_OPERATION_QUANTITY);
        handler.handle(transaction);
        actualQuantity = Storage.storage.getOrDefault(APPLE, ZERO_FRUIT_QUANTITY);
        expectedQuantity = ZERO_FRUIT_QUANTITY;
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    public void testPurchaseHandlerShouldIgnoreNegativeTransaction() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                APPLE,
                INVALID_OPERATION_QUANTITY);
        handler.handle(transaction);
        Integer appleQuantity = Storage.storage.getOrDefault("apple", 0);
        assertEquals(10, (int) appleQuantity);
    }
}