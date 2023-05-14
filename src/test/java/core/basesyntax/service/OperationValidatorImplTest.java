package core.basesyntax.service;

import core.basesyntax.model.Operation;
import core.basesyntax.service.impl.OperationValidatorImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationValidatorImplTest {
    private static final String BALANCE = "b";
    private static final String SUPPLY = "s";
    private static final String PURCHASE = "p";
    private static final String RETURN = "r";
    private static final String WRONG_OPERATION = "q";
    private static OperationValidation validator;

    @BeforeClass
    public static void setUp() {
        validator = new OperationValidatorImpl();
    }

    @Test
    public void setBalanceOperation_ok() {
        Operation actual = validator.validate(BALANCE);
        Assert.assertEquals(BALANCE, actual.getOperationChar());
    }

    @Test
    public void setSupplyOperation_ok() {
        Operation actual = validator.validate(SUPPLY);
        Assert.assertEquals(SUPPLY, actual.getOperationChar());
    }

    @Test
    public void setPurchaseOperation_ok() {
        Operation actual = validator.validate(PURCHASE);
        Assert.assertEquals(PURCHASE, actual.getOperationChar());
    }

    @Test
    public void setReturnOperation_ok() {
        Operation actual = validator.validate(RETURN);
        Assert.assertEquals(RETURN, actual.getOperationChar());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void setIncorrectOperation_notOk() {
        validator.validate(WRONG_OPERATION);
    }
}
