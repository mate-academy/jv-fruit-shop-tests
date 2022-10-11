package core.basesyntax.model;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;

public class GetByOperationTest {

    @Test(expected = RuntimeException.class)
    public void searchOperation_inUpperCaseLetter_NotOk() {
        String balanceInUpperCase = "B";
        FruitTransaction.Operation.getByOperation(balanceInUpperCase);
        fail("Runtime exception should thrown if method take incorrect info");
    }

    @Test(expected = RuntimeException.class)
    public void getByOperator_incorrectOperationWord_NotOK() {
        String incorrectOperation = "divide";
        FruitTransaction.Operation.getByOperation(incorrectOperation);
        fail("Runtime exception should thrown if method take incorrect info");
    }

    @Test
    public void correctInputData_Ok() {
        String balance = "b";
        FruitTransaction.Operation actual = FruitTransaction.Operation.getByOperation(balance);
        FruitTransaction.Operation expected = FruitTransaction.Operation.BALANCE;
        Assert.assertEquals(expected, actual);
    }
}
