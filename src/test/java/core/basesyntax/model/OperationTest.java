package core.basesyntax.model;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class OperationTest {
    @Test
    void findByCode_withValidCodes_ok() {
        assertEquals(Operation.BALANCE, Operation.findByCode("b"));
        assertEquals(Operation.SUPPLY, Operation.findByCode("s"));
        assertEquals(Operation.PURCHASE, Operation.findByCode("p"));
        assertEquals(Operation.RETURN, Operation.findByCode("r"));
    }

    @Test
    void findByCode_withInvalidCode_notOk() {
        assertThrows(NoSuchElementException.class,
                () -> Operation.findByCode("x"));
    }

    @Test
    void findByCode_withNullCode_notOk() {
        assertThrows(NullPointerException.class,
                () -> Operation.findByCode(null));
    }

    @Test
    void findByCode_caseInsensitivity_ok() {
        assertEquals(Operation.BALANCE, Operation.findByCode("B"));
        assertEquals(Operation.SUPPLY, Operation.findByCode("S"));
        assertEquals(Operation.PURCHASE, Operation.findByCode("P"));
        assertEquals(Operation.RETURN, Operation.findByCode("R"));
    }
}