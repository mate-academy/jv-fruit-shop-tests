package core.basesyntax.service;

import core.basesyntax.exception.ValidatorServiceException;
import core.basesyntax.service.impl.ValidatorServiceImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ValidatorServiceTest {
    private static String VALID_LINE = "b,banana,20";
    private static String INVALID_LINE = "sfdhfdjfkjdf";
    private static ValidatorService validator;

    @BeforeClass
    public static void beforeClass() {
        validator = new ValidatorServiceImpl();
    }

    @Test
    public void validate_validLine_ok() {
        Assert.assertTrue(validator.validate(VALID_LINE));
    }

    @Test(expected = ValidatorServiceException.class)
    public void validate_invalidLine_notOk() {
        Assert.assertTrue(validator.validate(INVALID_LINE));
    }

    @Test(expected = ValidatorServiceException.class)
    public void validate_emptyLine_notOk() {
        Assert.assertTrue(validator.validate(""));
    }

    @Test(expected = NullPointerException.class)
    public void validate_nullLine_notOk() {
        Assert.assertTrue(validator.validate(null));
    }

    @Test(expected = ValidatorServiceException.class)
    public void validate_noOperation_inLine_notOk() {
        Assert.assertTrue(validator.validate(",banana,10"));
    }

    @Test(expected = ValidatorServiceException.class)
    public void validate_noFruitName_inLine_notOk() {
        Assert.assertTrue(validator.validate("b,,10"));
    }

    @Test(expected = ValidatorServiceException.class)
    public void validate_noQuantity_inLine_notOk() {
        Assert.assertTrue(validator.validate("b,banana,"));
    }

    @Test(expected = ValidatorServiceException.class)
    public void validate_incorrectOperation_inLine_notOk() {
        Assert.assertTrue(validator.validate("k,banana,10"));
    }

    @Test(expected = ValidatorServiceException.class)
    public void validate_negativeQuantity_inLine_notOk() {
        Assert.assertTrue(validator.validate("b,banana,-10"));
    }
}
