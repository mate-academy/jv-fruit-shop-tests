package core.basesyntax.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import core.basesyntax.service.validator.Validator;
import core.basesyntax.service.validator.ValidatorImpl;
import org.junit.BeforeClass;
import org.junit.Test;

public class ValidatorTest {
    private static Validator validator;

    @BeforeClass
    public static void beforeClass() {
        validator = new ValidatorImpl();
    }

    @Test
    public void isValid_correctWork_ok() {
        String line = "p,apple,100";
        boolean actual = validator.isValid(line);
        assertTrue(actual);
    }

    @Test(expected = NullPointerException.class)
    public void isValid_null_notOk() {
        String line = null;
        validator.isValid(line);
    }

    @Test
    public void isValid_withOutOperation_notOk() {
        String line = "apple,100";
        boolean actual = validator.isValid(line);
        assertFalse(actual);
    }

    @Test
    public void isValid_withOutQuantity_notOk() {
        String line = "p,apple,";
        boolean actual = validator.isValid(line);
        assertFalse(actual);
    }

    @Test
    public void isValid_withOutFruit_notOk() {
        String line = "p,,100";
        boolean actual = validator.isValid(line);
        assertFalse(actual);
    }

    @Test
    public void isValid_emptyLine_notOk() {
        String line = "";
        boolean actual = validator.isValid(line);
        assertFalse(actual);
    }

    @Test
    public void isValid_incorrectLine_notOk() {
        String line = "o,apricot,***#@";
        boolean actual = validator.isValid(line);
        assertFalse(actual);
    }
}
