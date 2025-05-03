package core.basesyntax.strategy.handler.impl;

import core.basesyntax.db.ProductStorage;
import core.basesyntax.dto.ProductTransaction;
import core.basesyntax.enums.Operation;
import core.basesyntax.exception.SupplyOperationException;
import core.basesyntax.strategy.handler.OperationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyHandlerTest {
    private static final String TEST_PRODUCT = "apple";
    private static final int TEST_QUANTITY = 10;
    private static final int EXISTING_QUANTITY = 5;
    private static final int NEGATIVE_QUANTITY = -5;
    private static final int ZERO_QUANTITY = 0;
    private static OperationHandler supplyHandler;

    @BeforeAll
    static void beforeAll() {
        supplyHandler = new SupplyHandler();
    }

    @BeforeEach
    public void setUp() {
        ProductStorage.STORAGE.clear();
    }

    @Test
    public void handle_ProductExists_AddsToExistingQuantity() {
        ProductStorage.STORAGE.put(TEST_PRODUCT, EXISTING_QUANTITY);
        ProductTransaction transaction =
                new ProductTransaction(Operation.SUPPLY, TEST_PRODUCT, TEST_QUANTITY);
        supplyHandler.handle(transaction);
        int expectedQuantity = EXISTING_QUANTITY + TEST_QUANTITY;
        Assertions.assertEquals(expectedQuantity, ProductStorage.STORAGE.get(TEST_PRODUCT));
    }

    @Test
    public void handle_ProductDoesNotExist_AddsNewProductWithQuantity() {
        ProductTransaction transaction =
                new ProductTransaction(Operation.SUPPLY, TEST_PRODUCT, TEST_QUANTITY);
        supplyHandler.handle(transaction);
        Assertions.assertTrue(ProductStorage.STORAGE.containsKey(TEST_PRODUCT));
        Assertions.assertEquals(TEST_QUANTITY, ProductStorage.STORAGE.get(TEST_PRODUCT));
    }

    @Test
    public void handle_NegativeQuantity_ThrowsReturnOperationException() {
        ProductTransaction transaction =
                new ProductTransaction(Operation.SUPPLY, TEST_PRODUCT, NEGATIVE_QUANTITY);
        Assertions.assertThrows(SupplyOperationException.class, () ->
                        supplyHandler.handle(transaction),
                "Quantity must be a positive number " + NEGATIVE_QUANTITY
        );
    }

    @Test
    public void handle_ZeroQuantity_ThrowsSupplyOperationException() {
        ProductTransaction transaction =
                new ProductTransaction(
                        Operation.SUPPLY, TEST_PRODUCT, ZERO_QUANTITY);
        Assertions.assertThrows(SupplyOperationException.class, () ->
                        supplyHandler.handle(transaction),
                "Quantity must be a positive number " + ZERO_QUANTITY
        );
    }
}
