package basesyntax.service;

import core.basesyntax.service.OperationValidator;
import core.basesyntax.service.impl.OperationValidatorImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationValidatorImplTest {
    private static final String CORRECT_OPERATION = "s";
    private static final String WRONG_OPERATION = "wrong";
    private static OperationValidator testValidator;

    @BeforeClass
    public static void setUp() {
        testValidator = new OperationValidatorImpl();
    }

    @Test
    public void setCorrectOperation_ok() {
        String actual = testValidator.validate(CORRECT_OPERATION);
        Assert.assertEquals(CORRECT_OPERATION, actual);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void setIncorrectOperation_notOk() {
        testValidator.validate(WRONG_OPERATION);
    }
}
