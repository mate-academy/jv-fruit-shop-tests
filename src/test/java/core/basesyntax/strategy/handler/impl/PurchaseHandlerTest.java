package core.basesyntax.strategy.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.handler.OperationHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {
    private static final String APPLE = "apple";
    private static final int VALID_FRUIT_QUANTITY = 10;
    private static final int MORE_THAN_VALID_FRUIT_QUANTITY = 11;
    private static final int INVALID_OPERATION_QUANTITY = -7;
    private static final String BANANA = "banana";
    private static final int ZERO_FRUIT_QUANTITY = 0;
    private static final int VALID_FRUIT_QUANTITY_2 = 20;
    private static final int VALID_FRUIT_QUANTITY_3 = 5;
    private static OperationHandler handler;

    @BeforeAll
    public static void setUp() {
        handler = new PurchaseHandler();
    }

    @BeforeEach
    public void beforeEach() {
        Storage.FRUITS.clear();
        Storage.FRUITS.put(APPLE, VALID_FRUIT_QUANTITY);
        Storage.FRUITS.put(BANANA, VALID_FRUIT_QUANTITY_2);
    }

    @Test
    public void test_purchase_quantity_is_greater_than_current() {
        assertThrows(RuntimeException.class,()
                -> handler.handle(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        APPLE,
                        MORE_THAN_VALID_FRUIT_QUANTITY)));
    }

    @Test
    public void test_purchase_quantity_is_equal_to_current() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                APPLE,
                VALID_FRUIT_QUANTITY);
        handler.handle(transaction);
        int updatedQuantity = Storage.FRUITS.get(APPLE);
        assertEquals(VALID_FRUIT_QUANTITY - transaction.getQuantity(), updatedQuantity);
    }

    @Test
    public void test_purchase_quantity_is_less_than_current() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                APPLE,
                VALID_FRUIT_QUANTITY_3);
        handler.handle(transaction);
        int updatedQuantity = Storage.FRUITS.get(APPLE);
        assertEquals(VALID_FRUIT_QUANTITY - VALID_FRUIT_QUANTITY_3, updatedQuantity);
    }

    @Test
    public void test_Negative_Transaction_not_ok() {
        assertThrows(RuntimeException.class, () -> handler
                .handle(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        APPLE,
                        INVALID_OPERATION_QUANTITY)));
    }

    @Test
    public void test_Reduce_Fruit_Quantity_In_Storage() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                BANANA,
                VALID_FRUIT_QUANTITY_2);
        handler.handle(transaction);
        Integer actualQuantity = Storage.FRUITS.get(BANANA);
        int expectedQuantity = ZERO_FRUIT_QUANTITY;
        assertSame(expectedQuantity, actualQuantity);
    }
}
