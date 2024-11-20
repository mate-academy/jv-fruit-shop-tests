package core.basesyntax.converter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class StringToFruitValidatorTest {
    private final String[] validArr = new String[]{"s", "banana", "25"};
    private final String[] invalidEmptyArr = new String[]{};
    private final String[] invalidNullArr = null;
    private final String[] invalidSizeArr = new String[]{"s", "banana", "25", "qwerty"};
    private final String[] invalidOperatorArr = new String[]{"k", "banana", "25"};
    private final String[] invalidOperatorNullArr = new String[]{null, "banana", "25"};
    private final String[] invalidNameArr = new String[]{"k", "", "25"};
    private final String[] invalidNameNullArr = new String[]{"k", null, "25"};
    private final String[] invalidQuantityArr = new String[]{"k", "banana", "-25"};
    private final String[] invalidQuantityNullArr = new String[]{"k", "banana", null};

    private final StringToFruitValidator validator = new StringToFruitValidator();

    @Test
    void validArr_OK() {
        assertTrue(validator.validateInputData(validArr));
    }

    @Test
    void invalidEmptyArr_NotOK() {
        assertFalse(validator.validateInputData(invalidEmptyArr));
    }

    @Test
    void invalidNullArr_ThrowsException() {
        assertThrows(RuntimeException.class,
                () -> validator.validateInputData(invalidNullArr),
                "Invalid operator: " + null);
    }

    @Test
    void invalidSizeArr_NotOK() {
        assertFalse(validator.validateInputData(invalidSizeArr));
    }

    @Test
    void invalidOperatorArr_ThrowsException() {
        assertThrows(RuntimeException.class,
                () -> validator.validateInputData(invalidOperatorArr),
                "Invalid operator: " + null);
    }

    @Test
    void invalidOperatorNullArr_ThrowsException() {
        assertThrows(RuntimeException.class,
                () -> validator.validateInputData(invalidOperatorNullArr),
                "Invalid operator: " + null);
    }

    @Test
    void invalidNameArr_ThrowsException() {
        assertThrows(RuntimeException.class,
                () -> validator.validateInputData(invalidNameArr),
                "Invalid operator: " + null);
    }

    @Test
    void invalidNameNullArr_ThrowsException() {
        assertThrows(RuntimeException.class,
                () -> validator.validateInputData(invalidNameNullArr),
                "Invalid operator: " + null);
    }

    @Test
    void invalidQuantityArr_ThrowsException() {
        assertThrows(RuntimeException.class,
                () -> validator.validateInputData(invalidQuantityArr),
                "Invalid operator: " + null);
    }

    @Test
    void invalidQuantityNullArr_ThrowsException() {
        assertThrows(RuntimeException.class,
                () -> validator.validateInputData(invalidQuantityNullArr),
                "Invalid operator: " + null);
    }
}
