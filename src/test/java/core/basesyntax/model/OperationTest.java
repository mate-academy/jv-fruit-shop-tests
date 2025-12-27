package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class OperationTest {
    @Test
    void fromCode_valid_Ok() {
        assertEquals(Operation.BALANCE,Operation.fromCode("b"));
        assertEquals(Operation.SUPPLY,Operation.fromCode("s"));
        assertEquals(Operation.PURCHASE,Operation.fromCode("p"));
        assertEquals(Operation.RETURN,Operation.fromCode("r"));
    }

    @Test
    void fromCode_unknownOperation_NotOk() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class,
                    () -> Operation.fromCode("n"));

        assertTrue(exception.getMessage().contains("Unknown operation"));
    }
}

