package core.basesyntax.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.strategy.handler.HandlerDataValidatorImpl;
import core.basesyntax.strategy.handler.OperationHandler;
import core.basesyntax.strategy.handler.PurchaseOperationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PurchaseOperationHandlerTest {
    private static final int NEGATIVE_QTY = -1;
    private static final int ZERO_QTY = 0;
    private static final int POSITIVE_QTY = 20;
    private static final int PURCHASE_QTY = 5;
    private static final int PURCHASE_RESULT = 15;
    private static final String VALID_FRUIT = "apple";
    private static final String NULL_FRUIT = null;
    private final OperationHandler handler = new PurchaseOperationHandler(
            new HandlerDataValidatorImpl());

    @Test
    void handle_validZeroValueCase_Ok() {
        Storage.storage.put(VALID_FRUIT, POSITIVE_QTY);
        handler.handle(VALID_FRUIT, ZERO_QTY);
        int actualValue = Storage.storage.get(VALID_FRUIT);
        Assertions.assertEquals(POSITIVE_QTY, actualValue);
    }

    @Test
    void handle_validPurchaseCase_Ok() {
        Storage.storage.put(VALID_FRUIT, POSITIVE_QTY);
        handler.handle(VALID_FRUIT, PURCHASE_QTY);
        int actualValue = Storage.storage.get(VALID_FRUIT);
        Assertions.assertEquals(PURCHASE_RESULT, actualValue);
    }

    @Test
    void handle_invalidPurchaseCase_notOk() {
        Storage.storage.put(VALID_FRUIT, ZERO_QTY);
        RuntimeException purchaseException = assertThrows(RuntimeException.class,
                () -> handler.handle(VALID_FRUIT, PURCHASE_QTY));
        assertEquals("You cannot sell more fruit than is available",
                purchaseException.getMessage());
    }

    @Test
    void handle_negativeValueCase_notOk() {
        RuntimeException operationException = assertThrows(RuntimeException.class,
                () -> handler.handle(VALID_FRUIT, NEGATIVE_QTY));
        assertEquals("Quantity cannot be less than zero " + NEGATIVE_QTY,
                operationException.getMessage());
    }

    @Test
    void handle_nullKey_notOk() {
        RuntimeException operationException = assertThrows(RuntimeException.class,
                () -> handler.handle(NULL_FRUIT, POSITIVE_QTY));
        assertEquals("Fruit cannot be null", operationException.getMessage());
    }
}
