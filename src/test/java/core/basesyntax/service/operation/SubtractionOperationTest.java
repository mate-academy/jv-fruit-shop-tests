package core.basesyntax.service.operation;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class SubtractionOperationTest {
    private static Calculator calculator;

    @BeforeClass
    public static void setUp() {
        calculator = new SubtractionOperation();
    }

    @Test
    public void subtractionOperation_Ok() {
        int expected = 10;
        int actual = calculator.calculateValue(20, 10);
        Assert.assertEquals(expected, actual);
    }
}
