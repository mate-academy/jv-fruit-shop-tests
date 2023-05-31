package basesyntax.service;

import core.basesyntax.service.OperationValidator;
import core.basesyntax.service.impl.OperationValidatorImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationValidatorImplTest {
    private static final String BALANCE = "b";
    private static final String SUPPLY = "s";
    private static final String PURCHASE = "p";
    private static final String RETURN = "r";
    private static final String WRONG_OPERATION = "e";
    private static OperationValidator testValidator;

    @BeforeClass
    public static void setUp() {
        testValidator = new OperationValidatorImpl();
    }

    @Test
    public void setBalanceOperation_ok() {
        String actual = testValidator.validate(BALANCE);
        Assert.assertEquals(BALANCE, actual);
    }

    @Test
    public void setSupplyOperation_ok() {
        String actual = testValidator.validate(SUPPLY);
        Assert.assertEquals(SUPPLY, actual);
    }

    @Test
    public void setPurchaseOperation_ok() {
        String actual = testValidator.validate(PURCHASE);
        Assert.assertEquals(PURCHASE, actual);
    }

    @Test
    public void setReturnOperation_ok() {
        String actual = testValidator.validate(RETURN);
        Assert.assertEquals(RETURN, actual);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void setIncorrectOperation_notOk() {
        testValidator.validate(WRONG_OPERATION);
    }
}
