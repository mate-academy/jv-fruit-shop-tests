package core.basesyntax.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OperationTest {
    private static final String INVALID_CODE = "invalid";

    @Test
    void getCode_correctCodes_ok() {
        assertEquals("b", Operation.BALANCE.getCode());
        assertEquals("p", Operation.PURCHASE.getCode());
        assertEquals("s", Operation.SUPPLY.getCode());
        assertEquals("r", Operation.RETURN.getCode());
    }

    @Test
    void getValueFromCode_correctValues_ok() {
        assertEquals(Operation.BALANCE, Operation.getValueFromCode("b"));
        assertEquals(Operation.PURCHASE, Operation.getValueFromCode("p"));
        assertEquals(Operation.SUPPLY, Operation.getValueFromCode("s"));
        assertEquals(Operation.RETURN, Operation.getValueFromCode("r"));
    }

    @Test
    public void GetValueFromCode_invalidCode_notOk() {
        assertThrows(IllegalArgumentException.class, () ->
                Operation.getValueFromCode(INVALID_CODE));
    }
}