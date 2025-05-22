package basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class OperationTest {
    @Test
    void fromCode_validCodeBalance_Ok() {
        assertEquals(Operation.BALANCE, Operation.fromCode("b"));
    }

    @Test
    void fromCode_validCodePurchase_Ok() {
        assertEquals(Operation.PURCHASE, Operation.fromCode("p"));
    }

    @Test
    void fromCode_validCodeReturn_Ok() {
        assertEquals(Operation.RETURN, Operation.fromCode("r"));
    }

    @Test
    void fromCode_validCodeSupply_Ok() {
        assertEquals(Operation.SUPPLY, Operation.fromCode("s"));
    }

    @Test
    void fromCode_invalidCode_NotOk() {
        String wrongCode = "w";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> Operation.fromCode(wrongCode));
        assertTrue(exception.getMessage().contains("Unknown operation"));
    }
}
