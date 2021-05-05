package core.basesyntax.validate.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import core.basesyntax.validate.ValidationData;
import org.junit.BeforeClass;
import org.junit.Test;

public class ValidationDataImplTest {
    private static ValidationData validationData;

    @BeforeClass
    public static void beforeClass() throws Exception {
        validationData = new ValidationDataImpl();
    }

    @Test
    public void validationByOperationTest_Ok() {
        assertTrue(validationData.validationByOperation("b"));
    }

    @Test (expected = RuntimeException.class)
    public void validationByIncorrectOperationTypeTest_NotOk() {
        assertFalse(validationData.validationByOperation("q"));
    }

    @Test
    public void validationByFruitName_Ok() {
        assertTrue(validationData.validationByFruitName("banana"));
    }

    @Test (expected = RuntimeException.class)
    public void validationByIncorrectFruitName_NotOk() {
        validationData.validationByFruitName("42cat");
    }

    @Test
    public void validationByQuantity_Ok() {
        assertTrue(validationData.validationByQuantity("9"));
    }

    @Test (expected = RuntimeException.class)
    public void validationByIncorrectQuantity_NotOk() {
        validationData.validationByQuantity("asd");
    }

    @Test
    public void validationData_Ok() {
        assertTrue(validationData.validationData("b","banana","21"));
    }

    @Test (expected = RuntimeException.class)
    public void validationIncorrectData_NotOk() {
        validationData.validationData("m","123","asd");
    }
}
