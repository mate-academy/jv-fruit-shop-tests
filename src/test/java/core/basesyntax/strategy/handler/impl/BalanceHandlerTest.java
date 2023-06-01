package core.basesyntax.strategy.handler.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.handler.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceHandlerTest {
    private static final String APPLE = "apple";
    private static final int VALID_FRUIT_QUANTITY = 5;
    private static final String BANANA = "banana";
    private static final int INVALID_FRUIT_QUANTITY = -5;
    private static final int ZERO_FRUIT_QUANTITY = 0;
    private static OperationHandler handler;

    @BeforeAll
    public static void setUp() {
        handler = new BalanceHandler();
    }

    @AfterEach
    public void afterEach() {
        Storage.FRUITS.clear();
    }

    @Test
    public void test_addFruitToStorage_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                APPLE,
                VALID_FRUIT_QUANTITY);
        handler.handle(transaction);
        int expectedAppleQuantity = VALID_FRUIT_QUANTITY;
        Integer actualAppleQuantity = Storage.FRUITS.getOrDefault(APPLE, 0);
        assertSame(expectedAppleQuantity, actualAppleQuantity);
    }

    @Test
    public void test_add_fruit_with_zero_quantity_to_storage() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                APPLE,
                ZERO_FRUIT_QUANTITY);
        handler.handle(transaction);
        int expectedAppleQuantity = ZERO_FRUIT_QUANTITY;
        Integer actualAppleQuantity = Storage.FRUITS.getOrDefault(APPLE, 0);
        assertSame(expectedAppleQuantity, actualAppleQuantity);
    }

    @Test
    public void test_add_negative_Quantity_not_ok() {
        assertThrows(RuntimeException.class, () -> handler
                .handle(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        BANANA,
                        INVALID_FRUIT_QUANTITY)));
    }
}
