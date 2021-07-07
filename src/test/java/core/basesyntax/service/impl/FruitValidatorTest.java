package core.basesyntax.service.impl;

import org.junit.Assert;
import org.junit.Test;

public class FruitValidatorTest {
    private static final String EMPTY_STRING = "";
    private static final String PARTICLE_STRING = "b,          ";
    private static final String WRONG_OPERATION_STRING = "f,coconut,20";
    private static final String OTHER_FRUIT_LANGUAGE_STRING = "b,коконат,20";
    private static final String WHATEVER_INPUT_STRING = "g╧~'I{ОUYбАл╤Цd8*!,1↓Y▬4,";
    private static final String NEGATIVE_QUANTITY_STRING = "b,coconut,-20";
    private static final String ZERO_QUANTITY_STRING = "b,coconut,0";
    private static final String EDGE_CASE_MAX_VALUE = "b,coconut," + Integer.MAX_VALUE;
    private static final String EDGE_CASE_MIN_VALUE = "b,coconut," + Integer.MIN_VALUE;
    private FruitValidator validator = new FruitValidator();

    @Test
    public void emptyString_Ok() {
        Assert.assertFalse(validator.isValid(EMPTY_STRING));
    }

    @Test
    public void particleString_Ok() {
        Assert.assertFalse(validator.isValid(PARTICLE_STRING));
    }

    @Test
    public void otherLanguageFruitNaming_Ok() {
        Assert.assertFalse(validator.isValid(OTHER_FRUIT_LANGUAGE_STRING));
    }

    @Test
    public void incorrectOperation_Ok() {
        Assert.assertFalse(validator.isValid(WRONG_OPERATION_STRING));
    }

    @Test
    public void invalidData_Ok() {
        Assert.assertFalse(validator.isValid(WHATEVER_INPUT_STRING));
    }

    @Test
    public void negativeQuantity_Ok() {
        Assert.assertFalse(validator.isValid(NEGATIVE_QUANTITY_STRING));
    }

    @Test
    public void zeroQuantity() {
        Assert.assertTrue(validator.isValid(ZERO_QUANTITY_STRING));
    }

    @Test
    public void minMaxValues() {
        Assert.assertTrue(validator.isValid(EDGE_CASE_MAX_VALUE));
        Assert.assertFalse(validator.isValid(EDGE_CASE_MIN_VALUE));
    }
}
