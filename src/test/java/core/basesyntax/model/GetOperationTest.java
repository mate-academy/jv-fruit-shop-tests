package core.basesyntax.model;

import org.junit.Assert;
import org.junit.Test;

public class GetOperationTest {

    @Test
    public void findOperationByName_Ok() {
        Transaction.Operation expected = Transaction.Operation.BALANCE;
        Transaction.Operation actual = Transaction.findOperationByName("b");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findOperationByName_NotOk() {
        try {
            Transaction.Operation actual = Transaction.findOperationByName("wrongOperation");
        } catch (Exception e) {
            Assert.assertSame(RuntimeException.class, e.getClass());
        }
    }
}
