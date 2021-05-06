package core.basesyntax.model;

import core.basesyntax.exceptions.WrongOperationException;
import org.junit.Assert;
import org.junit.Test;

public class OperationTest {
    @Test(expected = WrongOperationException.class)
    public void testWrongOperation_notOK() {
        Operation.getOperation("k");
    }

    @Test
    public void testValidOperation() {
        Operation actual;
        Operation expected;

        expected = Operation.BALANCE;
        actual = Operation.getOperation("b");
        Assert.assertEquals(expected, actual);

        expected = Operation.SUPPLY;
        actual = Operation.getOperation("s");
        Assert.assertEquals(expected, actual);

        expected = Operation.PURCHASE;
        actual = Operation.getOperation("p");
        Assert.assertEquals(expected, actual);

        expected = Operation.RETURN;
        actual = Operation.getOperation("r");
        Assert.assertEquals(expected, actual);
    }
}
