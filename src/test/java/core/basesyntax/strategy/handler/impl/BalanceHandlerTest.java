package core.basesyntax.strategy.handler.impl;

import core.basesyntax.db.ProductStorage;
import core.basesyntax.dto.ProductTransaction;
import core.basesyntax.enums.Operation;
import core.basesyntax.exception.BalanceOperationException;
import core.basesyntax.strategy.handler.OperationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceHandlerTest {
    private static final String TEST_PRODUCT = "apple";
    private static final int TEST_QUANTITY = 10;
    private static final int EXISTING_QUANTITY = 5;
    private static OperationHandler balanceHandler;

    @BeforeAll
    static void beforeAll() {
        balanceHandler = new BalanceHandler();
    }

    @BeforeEach
    public void setUp() {
        ProductStorage.STORAGE.clear();
    }

    @Test
    public void handle_ValidTransaction_AddsProductToStorage() {
        ProductTransaction transaction =
                new ProductTransaction(Operation.BALANCE, TEST_PRODUCT, TEST_QUANTITY);
        balanceHandler.handle(transaction);
        Assertions.assertTrue(ProductStorage.STORAGE.containsKey(TEST_PRODUCT));
        Assertions.assertEquals(TEST_QUANTITY, ProductStorage.STORAGE.get(TEST_PRODUCT));
    }

    @Test
    public void handle_DuplicateBalanceOperation_ThrowsBalanceOperationException() {
        ProductStorage.STORAGE.put(TEST_PRODUCT, EXISTING_QUANTITY);
        ProductTransaction transaction =
                new ProductTransaction(Operation.BALANCE, TEST_PRODUCT, TEST_QUANTITY);
        Assertions.assertThrows(BalanceOperationException.class, () -> {
            balanceHandler.handle(transaction);
        });
    }
}
