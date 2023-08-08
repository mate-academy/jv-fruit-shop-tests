package core.basesyntax.service.implementations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.DataValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataValidatorImplTest {
    private static DataValidator validator;

    @BeforeAll
    static void beforeAll() {
        validator = new DataValidatorImpl();
    }

    @Test
    void validate_CorrectString_ok() {
        String[] data = new String[]{"b","banana", "150"};
        assertDoesNotThrow(() -> validator.validate(data));
    }

    @Test
    void validate_MissingString_notOk() {
        String[] data = new String[]{"b", null, "150"};
        assertThrows(IllegalArgumentException.class, () -> validator.validate(data));
    }

    @Test
    void validate_EmptyString_notOk() {
        String[] data = new String[]{"b", "", "150"};
        assertThrows(IllegalArgumentException.class, () -> validator.validate(data));
    }

    @Test
    void validate_BiggerString_notOk() {
        String[] data = new String[]{"b", "apple", "150", "banana"};
        assertThrows(IllegalArgumentException.class, () -> validator.validate(data));
    }

    @Test
    void validate_InvalidOperationCode_notOk() {
        String[] data = new String[]{"bb", "banana", "150"};
        assertThrows(IllegalArgumentException.class, () -> validator.validate(data));
    }

    @Test
    void validate_InvalidAmount_notOk() {
        String[] data = new String[]{"b", "banana", "apple"};
        assertThrows(IllegalArgumentException.class, () -> validator.validate(data));
    }
}
