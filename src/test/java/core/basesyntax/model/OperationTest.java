package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class OperationTest {
    @Test
    void fromCode_returnCorrectOperationForValidCode_ok() {
        assertEquals(Operation.BALANCE, Operation.fromCode("b"));
        assertEquals(Operation.SUPPLY, Operation.fromCode("s"));
        assertEquals(Operation.PURCHASE, Operation.fromCode("p"));
        assertEquals(Operation.RETURN, Operation.fromCode("r"));
    }

    @Test
    void getCode_returnCorrectCode_ok() {
        assertEquals("b", Operation.BALANCE.getCode());
        assertEquals("s", Operation.SUPPLY.getCode());
        assertEquals("p", Operation.PURCHASE.getCode());
        assertEquals("r", Operation.RETURN.getCode());
    }

    @Test
    void fromCode_invalidCode_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> Operation.fromCode("x"), "Unknown operation code: x");
    }
}
