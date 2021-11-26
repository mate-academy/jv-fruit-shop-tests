package core.basesyntax.service.impl;

import static org.junit.Assert.assertTrue;

import core.basesyntax.service.ValidatorService;
import core.basesyntax.exception.ValidatorServiceException;
import org.junit.BeforeClass;
import org.junit.Test;

public class ValidatorServiceImplTest {
    private static ValidatorService<String> validatorService;

    @BeforeClass
    public static void beforeClass() {
        validatorService = new ValidatorServiceImpl();
    }

    @Test(expected = ValidatorServiceException.class)
    public void validate_lineIsNull_notOk() {
        validatorService.validate(null);
    }

    @Test(expected = ValidatorServiceException.class)
    public void validate_lineIsEmpty_notOk() {
        validatorService.validate("");
    }

    @Test (expected = ValidatorServiceException.class)
    public void validate_lineWithoutOperation_notOk() {
        validatorService.validate(",banana,20");
    }

    @Test (expected = ValidatorServiceException.class)
    public void validate_lineWithNotExistOperation_notOk() {
        validatorService.validate("l,banana,20");
    }

    @Test (expected = ValidatorServiceException.class)
    public void validate_lineWithoutFruitName_notOk() {
        validatorService.validate("b,,20");
    }

    @Test (expected = ValidatorServiceException.class)
    public void validate_lineWithWrongFruitName_notOk() {
        validatorService.validate("b,ba34nd,20");
    }

    @Test (expected = ValidatorServiceException.class)
    public void validate_lineWithoutNumber_notOk() {
        validatorService.validate("b,banana,");
    }

    @Test (expected = ValidatorServiceException.class)
    public void validate_lineWithZeroNumber_notOk() {
        validatorService.validate("b,banana,0");
    }

    @Test (expected = ValidatorServiceException.class)
    public void validate_lineWithNegativeNumber_notOk() {
        validatorService.validate("b,banana,-20");
    }

    @Test (expected = ValidatorServiceException.class)
    public void validate_lineWithWrongNumber_notOk() {
        validatorService.validate("b,banana,gds673");
    }

    @Test (expected = ValidatorServiceException.class)
    public void validate_lineIsLongerThanExpected_notOk() {
        validatorService.validate("b,banana,20,45");
    }

    @Test
    public void validate_lineIsValid_Ok() {
        assertTrue(validatorService.validate("b,banana,20"));
    }
}
