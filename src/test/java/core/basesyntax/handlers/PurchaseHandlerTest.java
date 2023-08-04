package core.basesyntax.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {
    private static final String FRUIT_NAME = "banana";
    private static final int CURRENT_QUANTITY = 20;
    private static final int CORRECT_QUANTITY = 10;
    private static final int MORE_QUANTITY = 25;
    private static final int NEGATIVE_QUANTITY = -10;
    private static final int EXPECTED_QUANTITY = 10;
    private static final String EXCEPTION_MESSAGE_NEGATIVE_NUMBER =
            "Quantity of fruit can`t be a negative";
    private static final String EXCEPTION_MESSAGE_MORE_QUANTITY =
            "There is not enough fruit in the warehouse. You can buy "
                    + CURRENT_QUANTITY + " or less";
    private static final String EXCEPTION_MESSAGE_FRUIT_NOT_EXIST =
            "This fruit is not available in the warehouse";

    private OperationHandler purchaseHandler;

    @BeforeEach
    void setUp() {
        purchaseHandler = new PurchaseHandler();
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }

    @Test
    void handle_nonExistProduct_notOk() {
        var nonExistProduct = assertThrows(RuntimeException.class,
                () -> purchaseHandler.handle(FRUIT_NAME, CORRECT_QUANTITY));
        assertEquals(EXCEPTION_MESSAGE_FRUIT_NOT_EXIST, nonExistProduct.getMessage());
    }

    @Test
    void handle_correctQuantity_ok() {
        Storage.storage.put(FRUIT_NAME, CURRENT_QUANTITY);
        purchaseHandler.handle(FRUIT_NAME, CORRECT_QUANTITY);
        assertEquals(EXPECTED_QUANTITY, Storage.storage.get(FRUIT_NAME));
    }

    @Test
    void handle_moreQuantity_notOK() {
        Storage.storage.put(FRUIT_NAME, CURRENT_QUANTITY);
        var moreQuantity = assertThrows(RuntimeException.class,
                () -> purchaseHandler.handle(FRUIT_NAME, MORE_QUANTITY));
        assertEquals(EXCEPTION_MESSAGE_MORE_QUANTITY, moreQuantity.getMessage());
    }

    @Test
    void handle_negativeQuantity_notOk() {
        var negativeQuantity = assertThrows(RuntimeException.class,
                () -> purchaseHandler.handle(FRUIT_NAME, NEGATIVE_QUANTITY));
        assertEquals(EXCEPTION_MESSAGE_NEGATIVE_NUMBER, negativeQuantity.getMessage());
    }
}
