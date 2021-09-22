package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataValidatorImplTest {
    private static DataValidator dataValidator;
    private String mockFileLine;
    private String[] expected;
    private String[] actual;

    @BeforeAll
    static void setUp() {
        dataValidator = new DataValidatorImpl();
    }

    @Test
    void checkFruitRecordString_Ok() {
        mockFileLine = "b,banana,10";
        expected = new String[]{"b", "banana", "10"};
        actual = dataValidator.validate(mockFileLine);
        assertTrue(Arrays.deepEquals(expected, actual));
    }

    @Test
    void checkWrongOperation_NotOk() {
        mockFileLine = "a,banana,10";
        assertThrows(ValidationException.class, () -> dataValidator.validate(mockFileLine));
    }

    @Test
    void checkAnotherString_Ok() {
        mockFileLine = "s,avocado,112";
        expected = new String[]{"s", "avocado", "112"};
        actual = dataValidator.validate(mockFileLine);
        assertTrue(Arrays.deepEquals(expected, actual));
    }

    @Test
    void checkWrongFruitPattern_NotOk() {
        mockFileLine = "a,ban2ana,10";
        assertThrows(ValidationException.class, () -> dataValidator.validate(mockFileLine));
    }

    @Test
    void checkWrongQuantity_NotOk() {
        mockFileLine = "a,banana,-10";
        assertThrows(ValidationException.class, () -> dataValidator.validate(mockFileLine));
    }

    @Test
    void checkIncorrectFruitRecordLine_NotOk() {
        mockFileLine = "s,avocado10";
        assertThrows(ValidationException.class, () -> dataValidator.validate(mockFileLine));
    }

    @Test
    void checkEmptyFruitRecordLine_NotOk() {
        mockFileLine = "";
        assertThrows(ValidationException.class, () -> dataValidator.validate(mockFileLine));
    }

    @Test
    void checkNullFruitRecordLine_NotOk() {
        mockFileLine = null;
        assertThrows(ValidationException.class, () -> dataValidator.validate(mockFileLine));
    }
}
