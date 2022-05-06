package core.basesyntax.operation;

import org.junit.Assert;
import org.junit.Test;

public class OperationTest {
    @Test
    public void getOperation_Ok() {
        String operationCharacter = "b";
        Assert.assertSame(Operation.getOperation(operationCharacter), Operation.BALANCE);
    }

    @Test(expected = RuntimeException.class)
    public void getOperation_NotOK() {
        String operationCharacter = "g";
        Operation.getOperation(operationCharacter);
    }
}
