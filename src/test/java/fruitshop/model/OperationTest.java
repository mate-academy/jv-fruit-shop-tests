package fruitshop.model;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class OperationTest {
    private static final String CORRECT_OPERATION = "b";
    private static final String INCORRECT_OPERATION = "c";

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void parse_correctOperation_ok() {
        Operation expected = Operation.BALANCE;
        Operation actual = Operation.parse(CORRECT_OPERATION);
        assertEquals(expected, actual);
    }

    @Test
    public void parse_incorrectOperation_notOk() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("There is no such operation: " + INCORRECT_OPERATION);
        Operation.parse(INCORRECT_OPERATION);
    }
}
