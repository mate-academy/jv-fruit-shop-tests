package core.basesyntax.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class OperationTest {
    private static final String LISTED_STRING_OPERATION = "b";
    private static final String INCORRECT_STRING_OPERATION = "l";

    @Test
    public void parseOperation_operationIsListed_ok() {
        Operation expected = Operation.BALANCE;
        Operation actual = Operation.parseOperation(LISTED_STRING_OPERATION);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void parseOperation_operationNotListed_notOk() {
        Operation.parseOperation(INCORRECT_STRING_OPERATION);
    }
}
