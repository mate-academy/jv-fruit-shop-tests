package core.basesyntax.strategy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class OperationTest {
    private static final String BALANCE = "b";
    private static final String SUPPLY = "s";
    private static final String PURCHASE = "p";
    private static final String RETURN = "b";

    @Test
    void checkGetByCodeWithValidData_Ok() {
        assertDoesNotThrow(() -> Operation.getByCode(BALANCE));
        assertDoesNotThrow(() -> Operation.getByCode(SUPPLY));
        assertDoesNotThrow(() -> Operation.getByCode(PURCHASE));
        assertDoesNotThrow(() -> Operation.getByCode(RETURN));
    }

    @Test
    void checkGetByCodeWithNull_NotOk(){
        assertThrows(RuntimeException.class, () -> Operation.getByCode(null));
    }

    @Test
    void checkGetByCodeWithInvalidData_NotOk() {
        String invalidCharacter = "a";
        String invalidString = "Wrong string";
        assertThrows(RuntimeException.class, () -> Operation.getByCode(invalidString));
        assertThrows(RuntimeException.class, () -> Operation.getByCode(invalidCharacter));
    }
}