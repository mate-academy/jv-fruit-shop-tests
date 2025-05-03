package core.basesyntax.strategy.handler.impl;

import core.basesyntax.db.ProductStorage;
import core.basesyntax.dto.ProductTransaction;
import core.basesyntax.enums.Operation;
import core.basesyntax.exception.PurchaseOperationException;
import core.basesyntax.strategy.handler.OperationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseHandlerTest {
    public static final int NEGATIVE_QUANTITY = -5;
    private static final String TEST_PRODUCT = "apple";
    private static final int INITIAL_QUANTITY = 15;
    private static final int VALID_PURCHASE_QUANTITY = 5;
    private static final int EXCESSIVE_PURCHASE_QUANTITY = 20;
    private static final int ZERO_QUANTITY = 0;
    private static OperationHandler purchaseHandler;

    @BeforeAll
    static void beforeAll() {
        purchaseHandler = new PurchaseHandler();
    }

    @BeforeEach
    public void setUp() {
        ProductStorage.STORAGE.clear();
        ProductStorage.STORAGE.put(TEST_PRODUCT, INITIAL_QUANTITY);
    }

    @Test
    public void handle_ValidPurchase_ReducesProductQuantity() {
        ProductTransaction transaction =
                new ProductTransaction(Operation.PURCHASE, TEST_PRODUCT, VALID_PURCHASE_QUANTITY);
        purchaseHandler.handle(transaction);
        int expectedQuantity = INITIAL_QUANTITY - VALID_PURCHASE_QUANTITY;
        Assertions.assertEquals(expectedQuantity, ProductStorage.STORAGE.get(TEST_PRODUCT));
    }

    @Test
    public void handle_ExcessiveQuantity_ThrowsPurchaseOperationException() {
        ProductTransaction transaction =
                new ProductTransaction(
                        Operation.PURCHASE, TEST_PRODUCT, EXCESSIVE_PURCHASE_QUANTITY);
        Assertions.assertThrows(PurchaseOperationException.class, () ->
                purchaseHandler.handle(transaction)
        );
    }

    @Test
    public void handle_NegativeQuantity_ThrowsPurchaseOperationException() {
        ProductTransaction transaction =
                new ProductTransaction(
                        Operation.PURCHASE, TEST_PRODUCT, NEGATIVE_QUANTITY);
        Assertions.assertThrows(PurchaseOperationException.class, () ->
                        purchaseHandler.handle(transaction),
                "Quantity must be a positive number " + NEGATIVE_QUANTITY
        );
    }

    @Test
    public void handle_ZeroQuantity_ThrowsPurchaseOperationException() {
        ProductTransaction transaction =
                new ProductTransaction(
                        Operation.PURCHASE, TEST_PRODUCT, ZERO_QUANTITY);
        Assertions.assertThrows(PurchaseOperationException.class, () ->
                        purchaseHandler.handle(transaction),
                "Quantity must be a positive number " + ZERO_QUANTITY
        );
    }
}
