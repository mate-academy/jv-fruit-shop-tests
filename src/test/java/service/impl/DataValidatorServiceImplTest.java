package service.impl;

import exception.ValidationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ValidatorService;
import util.TestConstants;

class DataValidatorServiceImplTest {
    private static ValidatorService validatorService;
    private static String[] data;

    @BeforeAll
    static void beforeAll() {
        validatorService = new DataValidatorServiceImpl();
    }

    @BeforeEach
    void setUp() {
        data = new String[]{TestConstants.BALANCE_OPERATION_COD,
                TestConstants.BANANA,
                TestConstants.QUANTITY_20
        };
    }

    @Test
    void validate_incompleteData_notOk() {
        data = new String[]{TestConstants.BALANCE_OPERATION_COD, TestConstants.BANANA};
        Assertions.assertThrows(ValidationException.class, () -> validatorService.validate(data));
    }

    @Test
    void validate_validData_isOk() {
        Assertions.assertDoesNotThrow(() -> validatorService.validate(data));
    }

    @Test
    void validate_blankFruitName_notOk() {
        data = new String[]{TestConstants.BALANCE_OPERATION_COD,
                TestConstants.EMPTY_VALUE,
                TestConstants.QUANTITY_20
        };
        Assertions.assertThrows(ValidationException.class, () -> validatorService.validate(data));
    }

    @Test
    void validate_nullFruitName_notOk() {
        data = new String[]{TestConstants.BALANCE_OPERATION_COD,
                TestConstants.NULL_FRUIT_NAME,
                TestConstants.QUANTITY_35
        };
        Assertions.assertThrows(ValidationException.class, () -> validatorService.validate(data));
    }

    @Test
    void validate_blankQuantity_notOk() {
        data = new String[]{TestConstants.BALANCE_OPERATION_COD,
                TestConstants.APPLE,
                TestConstants.EMPTY_VALUE
        };
        Assertions.assertThrows(ValidationException.class, () -> validatorService.validate(data));
    }

    @Test
    void validate_nonNumericQuantity_notOk() {
        data = new String[]{TestConstants.PURCHASE_OPERATION_COD,
                TestConstants.APPLE,
                TestConstants.WRONG_NUMBER_FORMAT
        };
        Assertions.assertThrows(ValidationException.class, () -> validatorService.validate(data));
    }

    @Test
    void validate_negativeQuantity_notOk() {
        data = new String[]{TestConstants.SUPPLY_OPERATION_COD,
                TestConstants.APPLE,
                TestConstants.NEGATIVE_QUANTITY
        };
        Assertions.assertThrows(ValidationException.class, () -> validatorService.validate(data));
    }

    @AfterEach
    void tearDown() {
        data = new String[]{};
    }
}
