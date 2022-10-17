package core.basesyntax.model;

import org.junit.Assert;
import org.junit.Test;

public class GetByOperationTest {

    @Test(expected = RuntimeException.class)
    public void searchOperation_inUpperCaseLetter_NotOk() {
        String balanceInUpperCase = "B";
        FruitTransaction.Operation.getByOperation(balanceInUpperCase);
    }

    @Test(expected = RuntimeException.class)
    public void getByOperator_incorrectOperationWord_NotOK() {
        String incorrectOperation = "divide";
        FruitTransaction.Operation.getByOperation(incorrectOperation);
    }

    @Test
    public void getByOperator_correctOperationWord_OK() {
        String balance = "b";
        FruitTransaction.Operation actual = FruitTransaction.Operation.getByOperation(balance);
        FruitTransaction.Operation expected = FruitTransaction.Operation.BALANCE;
        Assert.assertEquals(expected, actual);
    }
}
