package core.basesyntax.model;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;

public class GetByOperationTest {
    private static final String BALANCE = "b";
    private static final String BALANCE_IN_UPPER_CASE = "B";
    private static final String INCORRECT_OPERATION = "divide";

    @Test(expected = RuntimeException.class)
    public void searchOperation_inUpperCaseLetter_NotOk() {
        FruitTransaction.Operation.getByOperation(BALANCE_IN_UPPER_CASE);
        fail("Runtime exception should thrown if method take incorrect info");
    }

    @Test(expected = RuntimeException.class)
    public void getByOperator_incorrectOperationWord_NotOK() {
        FruitTransaction.Operation.getByOperation(INCORRECT_OPERATION);
        fail("Runtime exception should thrown if method take incorrect info");
    }

    @Test
    public void correctInputData_Ok() {
        FruitTransaction.Operation actual = FruitTransaction.Operation.getByOperation(BALANCE);
        FruitTransaction.Operation expected = FruitTransaction.Operation.BALANCE;
        Assert.assertEquals(expected, actual);
    }
}
