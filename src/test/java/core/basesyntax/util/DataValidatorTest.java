package core.basesyntax.util;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.DataValidationException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataValidatorTest {
    private static DataValidator dataValidator;

    @BeforeAll
    static void setUpBeforeAll() {
        dataValidator = new DataValidator();
    }

    @Test
    void validateInputData_ValidInput_ok() {
        String operationCode = "r";
        String fruitName = "apple";
        int fruitQuantity = 10;
        assertDoesNotThrow(() -> dataValidator
                .validateInputData(operationCode, fruitName, fruitQuantity));
    }

    @Test
    void validateInputData_OperationCodeIsNull_notOk() {
        String operationCode = null;
        String fruitName = "Apple";
        int fruitQuantity = 10;
        DataValidationException exception = assertThrows(DataValidationException.class,
                () -> dataValidator.validateInputData(operationCode, fruitName, fruitQuantity));
        assertEquals("Invalid initial operationCode: null", exception.getMessage());
    }

    @Test
    void validateInputData_FruitNameIsEmpty() {
        String operationCode = "r";
        String fruitName = "";
        int fruitQuantity = 10;
        DataValidationException exception = assertThrows(DataValidationException.class,
                () -> dataValidator.validateInputData(operationCode, fruitName, fruitQuantity));
        assertEquals("Invalid initial fruitName: ", exception.getMessage());
    }

    @Test
    void validateInputData_NegativeFruitQuantity_notOk() {
        String operationCode = "r";
        String fruitName = "apple";
        int fruitQuantity = -5;
        DataValidationException exception = assertThrows(DataValidationException.class,
                () -> dataValidator.validateInputData(operationCode, fruitName, fruitQuantity));
        assertEquals("Invalid initial fruitQuantity: -5", exception.getMessage());
    }

    @Test
    void validateInputData_SourceDataIsNotEmpty_ok() {
        List<String> sourceData = Arrays.asList("r", "apple", "10");
        assertDoesNotThrow(() -> dataValidator.validateIsSourceDataEmpty(sourceData));
    }

    @Test
    void validateInputData_SourceDataIsEmpty_notOk() {
        List<String> sourceData = Collections.emptyList();
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> dataValidator.validateIsSourceDataEmpty(sourceData));
        assertEquals("Source file is empty", exception.getMessage());
    }
}
