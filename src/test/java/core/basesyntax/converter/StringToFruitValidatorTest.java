package core.basesyntax.converter;

import org.junit.jupiter.api.Assertions;
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
        Assertions.assertTrue(validator.validateInputData(validArr));
    }

    @Test
    void invalidEmptyArr_NotOK() {
        Assertions.assertFalse(validator.validateInputData(invalidEmptyArr));
    }

    @Test
    void invalidNullArr_ThrowsException() {
        Assertions.assertThrows(RuntimeException.class,
                () -> validator.validateInputData(invalidNullArr));
    }

    @Test
    void invalidSizeArr_NotOK() {
        Assertions.assertFalse(validator.validateInputData(invalidSizeArr));
    }

    @Test
    void invalidOperatorArr_ThrowsException() {
        Assertions.assertThrows(RuntimeException.class,
                () -> validator.validateInputData(invalidOperatorArr));
    }

    @Test
    void invalidOperatorNullArr_ThrowsException() {
        Assertions.assertThrows(RuntimeException.class,
                () -> validator.validateInputData(invalidOperatorNullArr));
    }

    @Test
    void invalidNameArr_ThrowsException() {
        Assertions.assertThrows(RuntimeException.class,
                () -> validator.validateInputData(invalidNameArr));
    }

    @Test
    void invalidNameNullArr_ThrowsException() {
        Assertions.assertThrows(RuntimeException.class,
                () -> validator.validateInputData(invalidNameNullArr));
    }

    @Test
    void invalidQuantityArr_ThrowsException() {
        Assertions.assertThrows(RuntimeException.class,
                () -> validator.validateInputData(invalidQuantityArr));
    }

    @Test
    void invalidQuantityNullArr_ThrowsException() {
        Assertions.assertThrows(RuntimeException.class,
                () -> validator.validateInputData(invalidQuantityNullArr));
    }
}
