package core.basesyntax.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class GetByOperationTest {
    private static final String BALANCE = "b";
    private static final String BALANCE_IN_UPPER_CASE = "B";
    private static final String INCORRECT_OPERATION = "divide";

    @Test
    public void searchOperation_inUpperCaseLetter_NotOk() {
        try {
            FruitTransaction.Operation.getByOperation(BALANCE_IN_UPPER_CASE);
        } catch (RuntimeException e) {
            return;
        }
        fail("Runtime exception should thrown if method take incorrect info");
    }

    @Test
    public void incorrectOperation_word_NotOK() {
        try {
            FruitTransaction.Operation.getByOperation(INCORRECT_OPERATION);
        } catch (RuntimeException e) {
            return;
        }
        fail("Runtime exception should thrown if method take incorrect info");
    }

    @Test
    public void correctInputData_Ok() {
        FruitTransaction.Operation actual = FruitTransaction.Operation.getByOperation(BALANCE);
        FruitTransaction.Operation expected = FruitTransaction.Operation.BALANCE;
        assertEquals(expected, actual);
    }
}
