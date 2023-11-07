package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.NoSuchOperationException;
import org.junit.jupiter.api.Test;

class OperationTest {

    @Test
    void operationFromString_validCodes_ok() {
        assertEquals(Operation.BALANCE, Operation.fromString("b"));
        assertEquals(Operation.SUPPLY, Operation.fromString("s"));
        assertEquals(Operation.PURCHASE, Operation.fromString("p"));
        assertEquals(Operation.RETURN, Operation.fromString("r"));
    }

    @Test
    void operationFromString_validCapitalCodes_ok() {
        assertEquals(Operation.BALANCE, Operation.fromString("B"));
        assertEquals(Operation.SUPPLY, Operation.fromString("S"));
        assertEquals(Operation.PURCHASE, Operation.fromString("P"));
        assertEquals(Operation.RETURN, Operation.fromString("R"));
    }

    @Test
    void operationFromString_invalidCode_notOk() {
        assertThrows(NoSuchOperationException.class,
                () -> Operation.fromString("x")
        );
    }

    @Test
    void operationFromString_nullCode_notOk() {
        assertThrows(NoSuchOperationException.class,
                () -> Operation.fromString(null)
        );
    }
}
