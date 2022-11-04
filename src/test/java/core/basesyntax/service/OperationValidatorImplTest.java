package core.basesyntax.service;

import core.basesyntax.service.impl.OperationValidatorImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationValidatorImplTest {
    private static final String BALANCE = "b";
    private static final String SUPPLY = "s";
    private static final String PURCHASE = "p";
    private static final String RETURN = "r";
    private static final String WRONG = "w";

    private static OperationValidator operationValidator;

    @BeforeClass
    public static void setUp() {
        operationValidator = new OperationValidatorImpl();
    }

    @Test
    public void setBalanceOperation_ok() {
        String actual = operationValidator.validate(BALANCE);
        Assert.assertEquals(actual, BALANCE);
    }

    @Test
    public void setSupplyOperation_ok() {
        String actual = operationValidator.validate(SUPPLY);
        Assert.assertEquals(actual, SUPPLY);
    }

    @Test
    public void setPurchaseOperation_ok() {
        String actual = operationValidator.validate(PURCHASE);
        Assert.assertEquals(actual, PURCHASE);
    }

    @Test
    public void setReturnOperation_ok() {
        String actual = operationValidator.validate(RETURN);
        Assert.assertEquals(actual, RETURN);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void setWrongOperation_notOk() {
        operationValidator.validate(WRONG);
    }
}
