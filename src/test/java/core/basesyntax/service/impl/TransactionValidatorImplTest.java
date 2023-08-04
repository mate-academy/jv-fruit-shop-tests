package core.basesyntax.service.impl;

import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.service.interfaces.TransactionValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionValidatorImplTest {
    private static final String HEADING = "type,fruit,quantity";
    private static final String INVALID_HEADING = "thur,fruitstor,bokuyt";
    private static final String EMPTY_HEADING = "";
    private static final String VALID_RECORD = "b,banana,100";
    private static final String VALID_PURCHASE_RECORD = "p,banana,100";
    private static final String VALID_SUPPLY_RECORD = "s,banana,100";
    private static final String VALID_RETURN_RECORD = "r,banana,100";
    private static final String INVALID_RECORD_FORMAT = "banana,b,100";
    private static final String INVALID_RECORD_OPERATION = "k,banana,100";
    private static final String INVALID_RECORD_QUANTITY = "b,banana,-50";

    private TransactionValidator validator;

    @BeforeEach
    void setUp() {
        validator = new TransactionValidatorImpl();
    }

    @Test
    void validate_OK() {
        String input = HEADING + System.lineSeparator()
                + VALID_RECORD + System.lineSeparator();
        validator.validate(input);
        Assertions.assertDoesNotThrow(() -> new InvalidDataException("Data is invalid"));
    }

    @Test
    void validate_operationPurchase_OK() {
        String input = HEADING + System.lineSeparator()
                + VALID_RECORD + System.lineSeparator()
                + VALID_PURCHASE_RECORD + System.lineSeparator();
        validator.validate(input);
        Assertions.assertDoesNotThrow(() -> new InvalidDataException("Data is invalid"));
    }

    @Test
    void validate_operationSupply_OK() {
        String input = HEADING + System.lineSeparator()
                + VALID_RECORD + System.lineSeparator()
                + VALID_SUPPLY_RECORD + System.lineSeparator();
        validator.validate(input);
        Assertions.assertDoesNotThrow(() -> new InvalidDataException("Data is invalid"));
    }

    @Test
    void validate_operationReturn_OK() {
        String input = HEADING + System.lineSeparator()
                + VALID_RECORD + System.lineSeparator()
                + VALID_RETURN_RECORD + System.lineSeparator();
        validator.validate(input);
        Assertions.assertDoesNotThrow(() -> new InvalidDataException("Data is invalid"));
    }

    @Test
    void validate_headingNotEquals_NotOK() {
        String input = INVALID_HEADING + System.lineSeparator()
                + VALID_RECORD + System.lineSeparator();
        InvalidDataException actual = Assertions.assertThrows(InvalidDataException.class, () -> {
            validator.validate(input);
        });
        String expectedMessage = "The heading is not in correct format!";
        Assertions.assertEquals(expectedMessage, actual.getMessage());
    }

    @Test
    void validate_headingEmpty_NotOK() {
        String input = EMPTY_HEADING + System.lineSeparator()
                + VALID_RECORD + System.lineSeparator();
        InvalidDataException actual = Assertions.assertThrows(InvalidDataException.class, () -> {
            validator.validate(input);
        });
        String expectedMessage = "The heading must not be empty!";
        Assertions.assertEquals(expectedMessage, actual.getMessage());
    }

    @Test
    void validate_inputIsNull_NotOK() {
        String input = null;
        InvalidDataException actual = Assertions.assertThrows(InvalidDataException.class, () -> {
            validator.validate(input);
        });
        String expectedMessage = "The input data must not be null!";
        Assertions.assertEquals(expectedMessage, actual.getMessage());
    }

    @Test
    void validate_inputIsEmpty_NotOK() {
        String input = "";
        InvalidDataException actual = Assertions.assertThrows(InvalidDataException.class, () -> {
            validator.validate(input);
        });
        String expectedMessage = "The input data must not be empty!";
        Assertions.assertEquals(expectedMessage, actual.getMessage());
    }

    @Test
    void validate_transactionIncorrectFormat_NotOK() {
        String input = HEADING + System.lineSeparator()
                + INVALID_RECORD_FORMAT + System.lineSeparator();
        InvalidDataException actual = Assertions.assertThrows(InvalidDataException.class, () -> {
            validator.validate(input);
        });
        String expectedMessage = "The record doesn't have required format!banana,b,100";
        Assertions.assertEquals(expectedMessage, actual.getMessage());
    }

    void validate_transactionInvalidOperationType_NotOK() {
        String input = HEADING + System.lineSeparator()
                + INVALID_RECORD_OPERATION + System.lineSeparator();
        InvalidDataException actual = Assertions.assertThrows(InvalidDataException.class, () -> {
            validator.validate(input);
        });
        String expectedMessage = "The Operation type is not valid! k,banana,100";
        Assertions.assertEquals(expectedMessage, actual.getMessage());
    }

    void validate_transactionQuantityLessThanZero_NotOK() {
        String input = HEADING + System.lineSeparator()
                + INVALID_RECORD_QUANTITY + System.lineSeparator();
        InvalidDataException actual = Assertions.assertThrows(InvalidDataException.class, () -> {
            validator.validate(input);
        });
        String expectedMessage = "The Operation type is not valid! k,banana,100";
        Assertions.assertEquals(expectedMessage, actual.getMessage());
    }
}
