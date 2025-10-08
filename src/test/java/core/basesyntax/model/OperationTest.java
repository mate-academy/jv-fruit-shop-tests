package core.basesyntax.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class OperationTest {

    @Test
    void fromCode_validCodes_ok() {
        assertEquals(Operation.BALANCE, Operation.fromCode("b"));
        assertEquals(Operation.SUPPLY, Operation.fromCode("s"));
        assertEquals(Operation.PURCHASE, Operation.fromCode("p"));
        assertEquals(Operation.RETURN, Operation.fromCode("r"));
    }

    @Test
    void fromCode_invalidCode_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> Operation.fromCode("x"));
        assertThrows(IllegalArgumentException.class, () -> Operation.fromCode(""));
        assertThrows(IllegalArgumentException.class, () -> Operation.fromCode(null));
    }

    @Test
    void getCode_ok() {
        assertEquals("b", Operation.BALANCE.getCode());
        assertEquals("s", Operation.SUPPLY.getCode());
        assertEquals("p", Operation.PURCHASE.getCode());
        assertEquals("r", Operation.RETURN.getCode());
    }
}
