package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class OperationTypeTest {

    @Test
    void fromCode_validCode_returnsCorrectOperationType() {
        assertEquals(OperationType.BALANCE, OperationType.fromCode("b"),
                "Code 'b' should return BALANCE");
        assertEquals(OperationType.SUPPLY, OperationType.fromCode("s"),
                "Code 's' should return SUPPLY");
        assertEquals(OperationType.PURCHASE, OperationType.fromCode("p"),
                "Code 'p' should return PURCHASE");
        assertEquals(OperationType.RETURN, OperationType.fromCode("r"),
                "Code 'r' should return RETURN");
    }

    @Test
    void fromCode_invalidCode_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> OperationType.fromCode("x"),
                "Invalid code 'x' should throw IllegalArgumentException");
    }

    @Test
    void getCode_returnsCorrectCodeForEachOperationType() {
        assertEquals("b", OperationType.BALANCE.getCode(), "BALANCE should return 'b'");
        assertEquals("s", OperationType.SUPPLY.getCode(), "SUPPLY should return 's'");
        assertEquals("p", OperationType.PURCHASE.getCode(), "PURCHASE should return 'p'");
        assertEquals("r", OperationType.RETURN.getCode(), "RETURN should return 'r'");
    }
}
