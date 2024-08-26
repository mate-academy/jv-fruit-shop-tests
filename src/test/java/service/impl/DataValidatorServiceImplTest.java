package service.impl;

import exception.ValidationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ValidatorService;

class DataValidatorServiceImplTest {
    private static ValidatorService validatorService;
    private static String[] data;

    @BeforeAll
    static void beforeAll() {
        validatorService = new DataValidatorServiceImpl();
    }

    @BeforeEach
    void setUp() {
        data = new String[]{"b", "banana", "20"};
    }

    @Test
    void validate_incompleteData_notOk() {
        data = new String[]{"b", "banana"};
        Assertions.assertThrows(ValidationException.class, () -> validatorService.validate(data));
    }

    @Test
    void validate_validData_isOk() {
        Assertions.assertDoesNotThrow(() -> validatorService.validate(data));
    }

    @Test
    void validate_blankFruitName_notOk() {
        data = new String[]{"b", "   ", "20"};
        Assertions.assertThrows(ValidationException.class, () -> validatorService.validate(data));
    }

    @Test
    void validate_nullFruitName_notOk() {
        data = new String[]{"b", "null", "35"};
        Assertions.assertThrows(ValidationException.class, () -> validatorService.validate(data));
    }

    @Test
    void validate_blankQuantity_notOk() {
        data = new String[]{"b", "apple", "  "};
        Assertions.assertThrows(ValidationException.class, () -> validatorService.validate(data));
    }

    @Test
    void validate_nonNumericQuantity_notOk() {
        data = new String[]{"p", "apple", "juice"};
        Assertions.assertThrows(ValidationException.class, () -> validatorService.validate(data));
    }

    @Test
    void validate_negativeQuantity_notOk() {
        data = new String[]{"s", "apple", "-100"};
        Assertions.assertThrows(ValidationException.class, () -> validatorService.validate(data));
    }

    @AfterEach
    void tearDown() {
        data = new String[]{};
    }
}
