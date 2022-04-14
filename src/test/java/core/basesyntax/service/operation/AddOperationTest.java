package core.basesyntax.service.operation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AddOperationTest {
    private static Calculator calculator;

    @Before
    public void setUp() {
        calculator = new AddOperation();
    }

    @Test
    public void addOperation_Ok() {
        int expected = 30;
        int actual = calculator.calculateValue(20, 10);
        Assert.assertEquals(expected, actual);
    }
}
