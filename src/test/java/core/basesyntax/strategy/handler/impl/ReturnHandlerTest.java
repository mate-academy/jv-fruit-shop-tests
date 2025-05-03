package core.basesyntax.strategy.handler.impl;

import core.basesyntax.db.ProductStorage;
import core.basesyntax.dto.ProductTransaction;
import core.basesyntax.enums.Operation;
import core.basesyntax.exception.ReturnOperationException;
import core.basesyntax.strategy.handler.OperationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnHandlerTest {
    private static final String TEST_PRODUCT = "apple";
    private static final int INITIAL_QUANTITY = 15;
    private static final int RETURN_QUANTITY = 5;
    private static final int NEGATIVE_QUANTITY = -5;
    private static final int ZERO_QUANTITY = 0;
    private static final String ABSENT_PRODUCT = "banana";
    private static OperationHandler returnHandler;

    @BeforeAll
    static void beforeAll() {
        returnHandler = new ReturnHandler();
    }

    @BeforeEach
    public void setUp() {
        ProductStorage.STORAGE.clear();
        ProductStorage.STORAGE.put(TEST_PRODUCT, INITIAL_QUANTITY);
    }

    @Test
    public void handle_ValidReturn_IncreasesProductQuantity() {
        ProductTransaction transaction =
                new ProductTransaction(Operation.RETURN, TEST_PRODUCT, RETURN_QUANTITY);
        returnHandler.handle(transaction);
        int expectedQuantity = INITIAL_QUANTITY + RETURN_QUANTITY;
        Assertions.assertEquals(expectedQuantity, ProductStorage.STORAGE.get(TEST_PRODUCT));
    }

    @Test
    public void handle_NegativeQuantity_ThrowsReturnOperationException() {
        ProductTransaction transaction =
                new ProductTransaction(Operation.RETURN, TEST_PRODUCT, NEGATIVE_QUANTITY);
        Assertions.assertThrows(ReturnOperationException.class, () ->
                        returnHandler.handle(transaction),
                "Quantity must be a positive number " + NEGATIVE_QUANTITY
        );
    }

    @Test
    public void handle_ZeroQuantity_ThrowsReturnOperationException() {
        ProductTransaction transaction =
                new ProductTransaction(
                        Operation.RETURN, TEST_PRODUCT, ZERO_QUANTITY);
        Assertions.assertThrows(ReturnOperationException.class, () ->
                        returnHandler.handle(transaction),
                "Quantity must be a positive number " + ZERO_QUANTITY
        );
    }

    @Test
    public void handle_AbsentProduct_ThrowsReturnOperationException() {
        ProductTransaction transaction =
                new ProductTransaction(Operation.RETURN, ABSENT_PRODUCT, RETURN_QUANTITY);
        Assertions.assertThrows(ReturnOperationException.class, () -> {
            returnHandler.handle(transaction);
        });
    }
}
