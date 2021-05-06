package core.basesyntax.shop.implementation;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

public class OperationTest {
    private static Operation operation;
    private static StringSplitter splitter;
    private static final String SPLIT_LINE = "b,apple,100";

    @BeforeClass
    public static void beforeClass() {
        splitter = new StringSplitter(SPLIT_LINE);
        operation = new Operation();
    }

    @Test
    public void operateData_Ok() {
        assertTrue(operation.operateData(splitter));
    }
}
