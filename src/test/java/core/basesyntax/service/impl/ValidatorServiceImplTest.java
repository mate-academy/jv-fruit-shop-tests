package core.basesyntax.service.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import core.basesyntax.service.ValidatorService;
import org.junit.BeforeClass;
import org.junit.Test;

public class ValidatorServiceImplTest {
    private static final String VALID_LINE = "b,banana,20";
    private static final String FOUR_PARTS_LINE = "b,banana,20,";
    private static final String TWO_PARTS_LINE = "b,banana";
    private static final String INVALID_OPERATION = "a,banana,20";
    private static final String INVALID_FRUIT = "b,banana1,20";
    private static final String NEGATIVE_QUANTITY = "b,banana,-20";
    private static final String EMPTY_LINE = "";
    private static ValidatorService validatorService;

    @BeforeClass
    public static void beforeClass() {
        validatorService = new ValidatorServiceImpl();
    }

    @Test
    public void validLine_Ok() {
        assertTrue(validatorService.validate(VALID_LINE));
    }

    @Test
    public void numberOfPartsUnder3_notOk() {
        assertFalse(validatorService.validate(TWO_PARTS_LINE));
    }

    @Test
    public void numberOfPartsOver3_notOk() {
        assertFalse(validatorService.validate(FOUR_PARTS_LINE));
    }

    @Test
    public void invalidPart_notOk() {
        assertFalse(validatorService.validate(INVALID_OPERATION));
        assertFalse(validatorService.validate(INVALID_FRUIT));
        assertFalse(validatorService.validate(NEGATIVE_QUANTITY));
    }

    @Test
    public void emptyLine_notOk() {
        assertFalse(validatorService.validate(EMPTY_LINE));
    }

    @Test
    public void nullLine_notOk() {
        assertFalse(validatorService.validate(null));
    }
}
