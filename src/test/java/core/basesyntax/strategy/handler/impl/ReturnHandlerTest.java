package core.basesyntax.strategy.handler.impl;

import static org.junit.Assert.assertSame;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.handler.OperationHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnHandlerTest {
    private static final String APPLE = "apple";
    private static final int VALID_FRUIT_QUANTITY = 5;
    private static final String BANANA = "banana";
    private static final int INVALID_FRUIT_QUANTITY = -7;
    private static final int ZERO_FRUIT_QUANTITY = 0;
    private static final int ADD_FRUIT_QUANTITY = 7;
    private static OperationHandler handler;

    @BeforeClass
    public static void beforeClass() {
        Storage.storage.clear();
        handler = new ReturnHandler();
    }

    @Before
    public void setUp() {
        Storage.storage.put(BANANA, VALID_FRUIT_QUANTITY);
    }

    @After
    public void afterEach() {
        Storage.storage.clear();
    }

    @Test
    public void test_Add_To_Empty_FruitStorage_ok() {
        Storage.storage.clear();
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN,
                APPLE,
                VALID_FRUIT_QUANTITY);
        handler.handle(transaction);
        Integer actualAppleQuantity = Storage.storage.getOrDefault(APPLE, 0);
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
        Integer actualAppleQuantity = Storage.storage.getOrDefault(BANANA, 0);
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
        Integer actualAppleQuantity = Storage.storage.getOrDefault(APPLE, 0);
        assertSame(expectedAppleQuantity, actualAppleQuantity);
    }

    @Test
    public void test_Not_Add_To_FruitStorage_ForNegativeQuantity_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN,
                BANANA,
                INVALID_FRUIT_QUANTITY);
        handler.handle(transaction);
        int expectedBananaQuantity = VALID_FRUIT_QUANTITY;
        Integer actualAppleQuantity = Storage.storage.getOrDefault(BANANA, 0);
        assertSame(expectedBananaQuantity, actualAppleQuantity);
    }
}
