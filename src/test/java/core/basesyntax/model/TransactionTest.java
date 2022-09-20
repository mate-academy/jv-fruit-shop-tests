package core.basesyntax.model;

import org.junit.Assert;
import org.junit.Test;

public class TransactionTest {
    @Test
    public void findOperationByName_Ok() {
        Transaction.Operation expected = Transaction.Operation.BALANCE;
        Transaction.Operation actual = Transaction.findOperationByName("b");
        Assert.assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void findOperationByName_NotOk() {
        Transaction.Operation actual = Transaction.findOperationByName("wrongOperation");
    }
}
