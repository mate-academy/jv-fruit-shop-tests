package core.basesyntax.dataprocessor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class OperationTest {

    @Test
    void fromCode_validCode_returnsCorrectOperation() {
        assertEquals(Operation.BALANCE, Operation.fromCode("b"));
        assertEquals(Operation.SUPPLY, Operation.fromCode("s"));
        assertEquals(Operation.PURCHASE, Operation.fromCode("p"));
        assertEquals(Operation.RETURN, Operation.fromCode("r"));
    }

    @Test
    void fromCode_invalidCode_throwsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> Operation.fromCode("x")
        );
        assertEquals("Invalid operation code: x", exception.getMessage());
    }

    @Test
    void getCode_returnsCorrectCode() {
        assertEquals("b", Operation.BALANCE.getCode());
        assertEquals("s", Operation.SUPPLY.getCode());
        assertEquals("p", Operation.PURCHASE.getCode());
        assertEquals("r", Operation.RETURN.getCode());
    }
}
