package core.basesyntax.model;

import static core.basesyntax.constants.Constants.INVALID_CODE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class OperationTest {

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
    public void getValueFromCode_invalidCode_notOk() {
        assertThrows(IllegalArgumentException.class, () ->
                Operation.getValueFromCode(INVALID_CODE));
    }
}
