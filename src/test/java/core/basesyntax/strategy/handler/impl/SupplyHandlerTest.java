package core.basesyntax.strategy.handler.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.handler.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupplyHandlerTest {
    private static final String APPLE = "apple";
    private static final int VALID_FRUIT_QUANTITY = 5;
    private static final String BANANA = "banana";
    private static final int INVALID_FRUIT_QUANTITY = -7;
    private static final int ZERO_FRUIT_QUANTITY = 0;
    private static final int ADD_FRUIT_QUANTITY = 7;
    private static OperationHandler handler;

    @BeforeAll
    public static void setUp() {
        handler = new SupplyHandler();
    }

    @AfterEach
    public void afterEach() {
        Storage.FRUITS.clear();
    }

    @Test
    public void test_Add_To_Empty_FruitStorage_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY,
                APPLE,
                VALID_FRUIT_QUANTITY);
        handler.handle(transaction);
        Integer actualAppleQuantity = Storage.FRUITS.getOrDefault(APPLE, 0);
        assertSame(VALID_FRUIT_QUANTITY, actualAppleQuantity);
    }

    @Test
    public void test_Add_Zero_To_Not_Empty_FruitStorage_For_ZeroQuantity_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY,
                APPLE,
                ZERO_FRUIT_QUANTITY);
        handler.handle(transaction);
        int expectedAppleQuantity = ZERO_FRUIT_QUANTITY;
        Integer actualAppleQuantity = Storage.FRUITS.getOrDefault(APPLE, 0);
        assertSame(expectedAppleQuantity, actualAppleQuantity);
    }

    @Test
    public void test_Add_To_FruitStorage_ForNegativeQuantity_not_ok() {
        assertThrows(RuntimeException.class, () -> handler
                .handle(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                BANANA,
                INVALID_FRUIT_QUANTITY)));
    }

    @Test
    public void test_Add_To_FruitStorage_ForNullFruitTransaction_not_ok() {
        assertThrows(NullPointerException.class, () -> handler.handle(null));
    }

    @Test
    public void test_Add_To_FruitStorage_ForMultipleFruits_ok() {
        FruitTransaction transaction1 = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY,
                APPLE,
                VALID_FRUIT_QUANTITY);
        FruitTransaction transaction2 = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY,
                BANANA,
                ADD_FRUIT_QUANTITY);
        handler.handle(transaction1);
        handler.handle(transaction2);
        Integer actualAppleQuantity = Storage.FRUITS.getOrDefault(APPLE, 0);
        Integer actualBananaQuantity = Storage.FRUITS.getOrDefault(BANANA, 0);
        assertSame(VALID_FRUIT_QUANTITY, actualAppleQuantity);
        assertSame(ADD_FRUIT_QUANTITY, actualBananaQuantity);
    }
}
