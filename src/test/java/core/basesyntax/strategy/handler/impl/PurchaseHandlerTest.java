package core.basesyntax.strategy.handler.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.handler.OperationHandler;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Optional;

public class PurchaseHandlerTest {
    private static final String APPLE = "apple";
    private static final int VALID_FRUIT_QUANTITY = 10;
    private static final int ANOTHER_VALID_FRUIT_QUANTITY = 20;
    private static final int MORE_THAN_VALID_FRUIT_QUANTITY = 11;
    private static final String BANANA = "banana";
    private static final int ZERO_FRUIT_QUANTITY = 0;
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
    public void test_UpdateFruitStorage_Ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                APPLE,
                VALID_FRUIT_QUANTITY);
        Integer beforeQuantity = Storage.storage.getOrDefault(APPLE, ZERO_FRUIT_QUANTITY);
        handler.handle(transaction);
        Integer actualQuantity = Storage.storage.getOrDefault(APPLE, ZERO_FRUIT_QUANTITY);
        int expectedQuantity = beforeQuantity - VALID_FRUIT_QUANTITY;
        assertSame(expectedQuantity, actualQuantity);
    }

    @Test(expected = RuntimeException.class)
    public void test_PurchaseQuantityIsGreaterThanCurrent() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                APPLE,
                MORE_THAN_VALID_FRUIT_QUANTITY);
        handler.handle(transaction);
    }

    @Test
    public void test_ReduceFruitQuantityInStorage() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                BANANA,
                ANOTHER_VALID_FRUIT_QUANTITY);
        handler.handle(transaction);
        Integer actualQuantity = Storage.storage.get(BANANA);
        int expectedQuantity = ZERO_FRUIT_QUANTITY;
        assertEquals(Optional.of(expectedQuantity), Optional.ofNullable(actualQuantity));
    }
}
