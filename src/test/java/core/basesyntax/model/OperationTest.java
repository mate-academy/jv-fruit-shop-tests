package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class OperationTest {
    @Test
    void fromCode_ShouldReturnCorrectOperationForValidCode() {
        assertEquals(Operation.BALANCE, Operation.fromCode("b"));
        assertEquals(Operation.SUPPLY, Operation.fromCode("s"));
        assertEquals(Operation.PURCHASE, Operation.fromCode("p"));
        assertEquals(Operation.RETURN, Operation.fromCode("r"));
    }

    @Test
    void fromCode_ShouldThrowExceptionForInvalidCode() {
        assertThrows(IllegalArgumentException.class,
                () -> Operation.fromCode("x"), "Unknown operation code: x");
    }

    @Test
    void getCode_ShouldReturnCorrectCode() {
        assertEquals("b", Operation.BALANCE.getCode());
        assertEquals("s", Operation.SUPPLY.getCode());
        assertEquals("p", Operation.PURCHASE.getCode());
        assertEquals("r", Operation.RETURN.getCode());
    }
}
