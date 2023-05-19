package core.basesyntax.strategy.handler.impl;

import static org.junit.Assert.assertSame;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.handler.OperationHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceHandlerTest {
    private static final String APPLE = "apple";
    private static final int VALID_FRUIT_QUANTITY = 5;
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
    public void test_Add_Fruit_To_Storage_Ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                APPLE,
                VALID_FRUIT_QUANTITY);
        handler.handle(transaction);
        int expectedAppleQuantity = VALID_FRUIT_QUANTITY;
        Integer actualAppleQuantity = Storage.storage.getOrDefault(APPLE, 0);
        assertSame(expectedAppleQuantity, actualAppleQuantity);
    }

    @Test
    public void test_Aad_Fruit_With_Zero_Quantity_To_Storage() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                APPLE,
                ZERO_FRUIT_QUANTITY);
        handler.handle(transaction);
        int expectedAppleQuantity = ZERO_FRUIT_QUANTITY;
        Integer actualAppleQuantity = Storage.storage.getOrDefault(APPLE, 0);
        assertSame(expectedAppleQuantity, actualAppleQuantity);
    }
}