package core.basesyntax.model;

import org.junit.Assert;
import org.junit.Test;

public class FruitTransactionTest {

    @Test(expected = RuntimeException.class)
    public void searchOperation_inUpperCaseLetter_NotOk() {
        String balanceInUpperCase = "B";
        FruitTransaction.Operation.getByCode(balanceInUpperCase);
    }

    @Test(expected = RuntimeException.class)
    public void getByOperator_incorrectOperationWord_NotOK() {
        String incorrectOperation = "divide";
        FruitTransaction.Operation.getByCode(incorrectOperation);
    }

    @Test
    public void correctInputData_Ok() {
        String balance = "b";
        FruitTransaction.Operation actual = FruitTransaction.Operation.getByCode(balance);
        FruitTransaction.Operation expected = FruitTransaction.Operation.BALANCE;
        Assert.assertEquals(expected, actual);
    }
}
