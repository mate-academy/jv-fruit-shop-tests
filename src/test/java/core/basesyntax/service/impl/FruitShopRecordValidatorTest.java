package core.basesyntax.service.impl;

import static org.junit.Assert.assertTrue;

import core.basesyntax.service.Validator;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitShopRecordValidatorTest {
    private static Validator validator;
    private static final String VALID_STRING = "s,f,123";
    private static final String INVALID_STRING = "bspr,f,123";

    @BeforeClass
    public static void setUp() {
        validator = new FruitShopRecordValidator();
    }

    @Test
    public void isValid_validString_Ok() {
        assertTrue(validator.isValid(VALID_STRING));
    }

    @Test(expected = RuntimeException.class)
    public void isValid_invalidString_NotOk() {
        validator.isValid(INVALID_STRING);
    }

}
