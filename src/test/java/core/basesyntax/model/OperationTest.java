package core.basesyntax.model;

import core.basesyntax.exceptions.IllegalOperationException;
import org.junit.Assert;
import org.junit.Test;

public class OperationTest {
    private static final String BALANCE = "b";
    private static final String SUPPLY = "s";
    private static final String RETURN = "r";
    private static final String PURCHASE = "p";
    private static final String INVALID_OPERATOR = "g";

    @Test
    public void getEnum_TestValidOperators() {
        Operation actual;
        Operation expected;

        actual = Operation.getEnum(BALANCE);
        expected = Operation.BALANCE;
        Assert.assertEquals(expected, actual);

        actual = Operation.getEnum(SUPPLY);
        expected = Operation.SUPPLY;
        Assert.assertEquals(expected, actual);

        actual = Operation.getEnum(RETURN);
        expected = Operation.RETURN;
        Assert.assertEquals(expected, actual);

        actual = Operation.getEnum(PURCHASE);
        expected = Operation.PURCHASE;
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = IllegalOperationException.class)
    public void getEnum_TestInvalidOperator() {
        Operation.getEnum(INVALID_OPERATOR);
    }
}
