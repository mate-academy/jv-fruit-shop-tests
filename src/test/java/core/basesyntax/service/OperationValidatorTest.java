package core.basesyntax.service;

import core.basesyntax.exception.UnknownOperationException;
import core.basesyntax.service.impl.OperationValidatorImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationValidatorTest {
    private static final String BALANCE = "b";
    private static final String SUPPLY = "s";
    private static final String PURCHASE = "p";
    private static final String RETURN = "r";
    private static final String WRONG_OPERATION = "e";
    private static OperationValidator validator;

    @BeforeClass
    public static void setUp() {
        validator = new OperationValidatorImpl();
    }

    @Test
    public void validate_balanceOperation_ok() {
        String actual = validator.validate(BALANCE);
        Assert.assertEquals(BALANCE, actual);
    }

    @Test
    public void validate_supplyOperation_ok() {
        String actual = validator.validate(SUPPLY);
        Assert.assertEquals(SUPPLY, actual);
    }

    @Test
    public void validate_purchaseOperation_ok() {
        String actual = validator.validate(PURCHASE);
        Assert.assertEquals(PURCHASE, actual);
    }

    @Test
    public void validate_returnOperation_ok() {
        String actual = validator.validate(RETURN);
        Assert.assertEquals(RETURN, actual);
    }

    @Test(expected = UnknownOperationException.class)
    public void validate_unknownOperation_notOk() {
        validator.validate(WRONG_OPERATION);
    }
}
