package core.basesyntax.service.impl;

import core.basesyntax.service.Validator;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ValidatorImplTest {
    private static Validator validator;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeClass
    public static void initializeFields() {
        validator = new ValidatorImpl();
    }

    @Test
    public void validate_lineIsNotFormatted_throwException() {
        expectedException.expect(RuntimeException.class);
        validator.validate("b, banana, 20");
    }

    @Test
    public void validate_requireLineFormat_ok() {
        Assert.assertTrue(validator.validate("b,banana,20"));
    }
}
