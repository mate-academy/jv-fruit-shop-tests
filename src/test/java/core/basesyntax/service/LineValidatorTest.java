package core.basesyntax.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import core.basesyntax.service.impl.LineValidatorImpl;
import org.junit.BeforeClass;
import org.junit.Test;

public class LineValidatorTest {
    private static LineValidator lineValidator;

    @BeforeClass
    public static void beforeClass() {
        lineValidator = new LineValidatorImpl();
    }

    @Test
    public void lineValidator_correctWork_ok() {
        String line = "b,banana,20";
        boolean actual = lineValidator.isValid(line);
        assertTrue(actual);
    }

    @Test
    public void lineValidator_withOutOperation_notOk() {
        String line = "banana,20";
        boolean actual = lineValidator.isValid(line);
        assertFalse(actual);
    }

    @Test
    public void lineValidator_withOutFruit_notOk() {
        String line = "b,,20";
        boolean actual = lineValidator.isValid(line);
        assertFalse(actual);
    }

    @Test
    public void lineValidator_withOutQuantity_notOk() {
        String line = "b,banana,";
        boolean actual = lineValidator.isValid(line);
        assertFalse(actual);
    }

    @Test
    public void lineValidator_emptyLine_notOk() {
        String line = "";
        boolean actual = lineValidator.isValid(line);
        assertFalse(actual);
    }

    @Test(expected = NullPointerException.class)
    public void lineValidator_Null_notOk() {
        String line = null;
        lineValidator.isValid(line);
    }
}
