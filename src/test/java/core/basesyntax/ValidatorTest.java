package core.basesyntax;

import core.basesyntax.service.Validator;
import core.basesyntax.service.impl.ValidatorImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ValidatorTest {
    private static String line;
    private static Validator validator;

    @BeforeClass
    public static void beforeClass() {
        validator = new ValidatorImpl();
    }

    @Test
    public void correctValidation_oK() {
        line = "b,banana,10";
        Assert.assertTrue(validator.validate(line));
    }

    @Test(expected = RuntimeException.class)
    public void incorrectLengthLine_notOK() {
        line = "b,banana,10,36";
        validator.validate(line);
    }

    @Test(expected = RuntimeException.class)
    public void incorrectTypeOfActivity_notOK() {
        line = "o,banana,10";
        validator.validate(line);
    }

    @Test(expected = RuntimeException.class)
    public void incorrectNameOfFruit_nameWithNumbers_notOK() {
        line = "b,ba1na2na,10";
        validator.validate(line);
    }

    @Test(expected = RuntimeException.class)
    public void incorrectNameOfFruit_nameWithSigns_notOK() {
        line = "b,banana*(,10";
        validator.validate(line);
    }

    @Test(expected = RuntimeException.class)
    public void incorrectQuantityOfFruit_quantityWithLetters_notOK() {
        line = "b,banana,10a";
        validator.validate(line);
    }

    @Test(expected = RuntimeException.class)
    public void incorrectQuantityOfFruit_quantityWithSigns_notOK() {
        line = "b,banana,10&";
        validator.validate(line);
    }
}
