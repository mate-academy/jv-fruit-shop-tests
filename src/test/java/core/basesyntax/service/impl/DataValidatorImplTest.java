package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.DataValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataValidatorImplTest {
    private static DataValidator dataValidator;

    @BeforeAll
    static void beforeAll() {
        dataValidator = new DataValidatorImpl();
    }

    @Test
    void dataValidator_correctString_ok() {
        String[] data = new String[]{"a", "apple", "40"};
        assertDoesNotThrow(() -> dataValidator.validate(data));
    }

    @Test
    void dataValidator_missingString_notOk() {
        String[] data = new String[]{"b", null, "20"};
        assertThrows(IllegalArgumentException.class, () -> dataValidator.validate(data));
    }

    @Test
    void dataValidator_moreString_notOk() {
        String[] data = new String[]{"b", "banana", "a", "100"};
        assertThrows(IllegalArgumentException.class, () -> dataValidator.validate(data));
    }

    @Test
    void dataValidator_unvalidDataInString_notOk() {
        String[] data = new String[]{"aa", "apple", "20"};
        assertThrows(IllegalArgumentException.class, ()
                -> dataValidator.validate(data));
    }

    @Test
    void dataValidator_unvalidQuantityInString_notOk() {
        String[] data = new String[]{"a", "apple", "a"};
        assertThrows(IllegalArgumentException.class, () -> dataValidator.validate(data));
    }
}
