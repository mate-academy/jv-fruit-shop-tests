package core.basesyntax.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.strategy.handler.HandlerDataValidator;
import core.basesyntax.strategy.handler.HandlerDataValidatorImpl;
import org.junit.jupiter.api.Test;

public class HandlerDataValidatorImplTest {
    private static final String NULL_VALUE = null;
    private static final int NEGATIVE_QTY = -1;
    private final HandlerDataValidator validator = new HandlerDataValidatorImpl();

    @Test
    void checkNull_nullCase_notOk() {
        RuntimeException nullCaseException = assertThrows(RuntimeException.class,
                () -> validator.checkNull(NULL_VALUE));
        assertEquals("Fruit cannot be null", nullCaseException.getMessage());
    }

    @Test
    void checkNegative_negativeValue_notOk() {
        RuntimeException negativeCaseException = assertThrows(RuntimeException.class,
                () -> validator.checkNegative(NEGATIVE_QTY));
        assertEquals("Quantity cannot be less than zero " + NEGATIVE_QTY,
                negativeCaseException.getMessage());
    }
}
