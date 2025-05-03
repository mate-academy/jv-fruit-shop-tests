package core.basesyntax.model;

import core.basesyntax.exceptions.WrongOperationException;
import org.junit.Assert;
import org.junit.Test;

public class OperationTest {
    private static final String INVALID_OPERATION = "k";
    private static final String SUPPLY = "s";
    private static final String PURCHASE = "p";
    private static final String BALANCE = "b";
    private static final String RETURN = "r";

    @Test(expected = WrongOperationException.class)
    public void testWrongOperation_notOK() {
        Operation.getOperation(INVALID_OPERATION);
    }

    @Test
    public void testValidOperation_OK() {
        Operation actual;
        Operation expected;

        expected = Operation.BALANCE;
        actual = Operation.getOperation(BALANCE);
        Assert.assertEquals(expected, actual);

        expected = Operation.SUPPLY;
        actual = Operation.getOperation(SUPPLY);
        Assert.assertEquals(expected, actual);

        expected = Operation.PURCHASE;
        actual = Operation.getOperation(PURCHASE);
        Assert.assertEquals(expected, actual);

        expected = Operation.RETURN;
        actual = Operation.getOperation(RETURN);
        Assert.assertEquals(expected, actual);
    }
}
