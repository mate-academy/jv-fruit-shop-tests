package core.basesyntax.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.strategy.handler.BalanceOperationHandler;
import core.basesyntax.strategy.handler.HandlerDataValidatorImpl;
import core.basesyntax.strategy.handler.OperationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceOperationHandlerTest {
    private static final int NEGATIVE_QTY = -1;
    private static final int ZERO_QTY = 0;
    private static final int POSITIVE_QTY = 5;
    private static final String VALID_FRUIT = "apple";
    private static final String NULL_FRUIT = null;
    private OperationHandler handler;

    @BeforeEach
    void setUp() {
        handler = new BalanceOperationHandler(new HandlerDataValidatorImpl());
        Storage.storage.clear();
    }

    @Test
    void handle_validZeroValueCase_Ok() {
        Storage.storage.put(VALID_FRUIT, ZERO_QTY);
        handler.handle(VALID_FRUIT, ZERO_QTY);
        int actualValue = Storage.storage.get(VALID_FRUIT);
        Assertions.assertEquals(ZERO_QTY, actualValue);
    }

    @Test
    void handle_validBalance_Ok() {
        Storage.storage.put(VALID_FRUIT, POSITIVE_QTY);
        handler.handle(VALID_FRUIT, POSITIVE_QTY);
        int actualValue = Storage.storage.get(VALID_FRUIT);
        Assertions.assertEquals(POSITIVE_QTY, actualValue);
    }

    @Test
    void handle_negativeValueCase_Ok() {
        RuntimeException operationException = assertThrows(RuntimeException.class,
                () -> handler.handle(VALID_FRUIT, NEGATIVE_QTY));
        assertEquals("Quantity cannot be less than zero " + NEGATIVE_QTY,
                operationException.getMessage());
    }

    @Test
    void handle_nullKey_Ok() {
        RuntimeException operationException = assertThrows(RuntimeException.class,
                () -> handler.handle(NULL_FRUIT, POSITIVE_QTY));
        assertEquals("Fruit cannot be null", operationException.getMessage());
    }
}
