package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class OperationTest {
    @Test
    void valueOfCode_nullCode_notOk() {
        assertThrows(RuntimeException.class, () -> Operation.valueOfCode(null));
    }

    @Test
    void valueOfCode_emptyCode_notOk() {
        assertThrows(RuntimeException.class, () -> Operation.valueOfCode(""));
    }

    @Test
    void valuesOfCode_ok() {
        assertEquals(Operation.BALANCE, Operation.valueOfCode("b"));
        assertEquals(Operation.SUPPLY, Operation.valueOfCode("s"));
        assertEquals(Operation.PURCHASE, Operation.valueOfCode("p"));
        assertEquals(Operation.RETURN, Operation.valueOfCode("r"));
        assertNull(Operation.valueOfCode("d"));
    }

    @Test
    void getCode_operationNull_notOk() {
        Operation operation = null;
        assertThrows(RuntimeException.class, () -> operation.getCode());
    }

    @Test
    void getCode_ok() {
        Operation operation = Operation.BALANCE;
        assertEquals("b", operation.getCode());
        operation = Operation.SUPPLY;
        assertEquals("s", operation.getCode());
        operation = Operation.PURCHASE;
        assertEquals("p", operation.getCode());
        operation = Operation.RETURN;
        assertEquals("r", operation.getCode());
    }
}
