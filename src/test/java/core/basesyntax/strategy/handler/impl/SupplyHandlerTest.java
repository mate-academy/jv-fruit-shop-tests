package core.basesyntax.strategy.handler.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.handler.OperationHandler;
import java.util.Optional;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;

public class SupplyHandlerTest {
    private static final String APPLE = "apple";
    private static final int VALID_FRUIT_QUANTITY = 5;
    private static final String BANANA = "banana";
    private static final int ZERO_FRUIT_QUANTITY = 0;
    private static final int ADD_FRUIT_QUANTITY = 7;
    private static OperationHandler handler;

    @BeforeClass
    public static void beforeClass() {
        handler = new SupplyHandler();
    }

    @Before
    public void setUp() {
        Storage.storage.put(BANANA, VALID_FRUIT_QUANTITY);
    }

    @AfterAll
    public static void afterAll() {
        Storage.storage.clear();
    }

    @Test
    public void test_AddToEmptyFruitStorage_Ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY,
                APPLE,
                VALID_FRUIT_QUANTITY);
        handler.handle(transaction);
        Integer actualAppleQuantity = Storage.storage.getOrDefault(APPLE, 0);
        assertSame(VALID_FRUIT_QUANTITY, actualAppleQuantity);
    }

    @Test
    public void test_AddToNotEmpt_FruitStorage_Ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY,
                BANANA,
                ADD_FRUIT_QUANTITY);
        handler.handle(transaction);
        int expectedBananaQuantity = VALID_FRUIT_QUANTITY
                + ADD_FRUIT_QUANTITY;
        Integer actualAppleQuantity = Storage.storage.getOrDefault(BANANA, 0);
        assertSame(expectedBananaQuantity, actualAppleQuantity);
    }

    @Test
    public void test_AddZeroToNotEmptyFruitStorageForZeroQuantity_Ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                APPLE,
                ZERO_FRUIT_QUANTITY);
        handler.handle(transaction);
        int expectedAppleQuantity = ZERO_FRUIT_QUANTITY;
        Integer actualAppleQuantity = Storage.storage.getOrDefault(APPLE, 0);
        assertEquals(Optional.of(expectedAppleQuantity), Optional.ofNullable(actualAppleQuantity));
    }
}
