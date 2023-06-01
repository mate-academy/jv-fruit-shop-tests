package core.basesyntax.strategy.handler.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.handler.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnHandlerTest {

    private static final String APPLE = "apple";
    private static final int VALID_FRUIT_QUANTITY = 5;
    private static final String BANANA = "banana";
    private static final int NEGATIVE_FRUIT_QUANTITY = -7;
    private static final int ZERO_FRUIT_QUANTITY = 0;
    private static final int ADD_FRUIT_QUANTITY = 7;
    private static OperationHandler handler;

    @BeforeAll
    public static void beforeAll() {
        handler = new ReturnHandler();
    }

    @BeforeEach
    public void setUp() {
        Storage.FRUITS.put(BANANA, VALID_FRUIT_QUANTITY);
    }

    @AfterEach
    public void afterEach() {
        Storage.FRUITS.clear();
    }

    @Test
    public void test_Add_To_Empty_FruitStorage_ok() {
        Storage.FRUITS.clear();
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN,
                APPLE,
                VALID_FRUIT_QUANTITY);
        handler.handle(transaction);
        Integer actualAppleQuantity = Storage.FRUITS.getOrDefault(APPLE, 0);
        assertSame(VALID_FRUIT_QUANTITY, actualAppleQuantity);
    }

    @Test
    public void test_Add_To_not_empty_FruitStorage_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN,
                BANANA,
                ADD_FRUIT_QUANTITY);
        handler.handle(transaction);
        int expectedBananaQuantity = VALID_FRUIT_QUANTITY
                + ADD_FRUIT_QUANTITY;
        Integer actualAppleQuantity = Storage.FRUITS.getOrDefault(BANANA, 0);
        assertSame(expectedBananaQuantity, actualAppleQuantity);
    }

    @Test
    public void test_Add_Zero_To_Not_Empty_FruitStorage_For_ZeroQuantity_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN,
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
                        NEGATIVE_FRUIT_QUANTITY)));
    }
}
