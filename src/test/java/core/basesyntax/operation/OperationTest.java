package core.basesyntax.operation;

import org.junit.Assert;
import org.junit.Test;

public class OperationTest {
    @Test
    public void getOperation_Ok() {
        String balanceCharacter = "b";
        Assert.assertSame("Test failed with operation: " + Operation.BALANCE,
                Operation.getOperation(balanceCharacter), Operation.BALANCE);
        String supplyCharacter = "s";
        Assert.assertSame("Test failed with operation: " + Operation.SUPPLY,
                Operation.getOperation(supplyCharacter), Operation.SUPPLY);
        String purchaseCharacter = "p";
        Assert.assertSame("Test failed with operation: " + Operation.PURCHASE,
                Operation.getOperation(purchaseCharacter), Operation.PURCHASE);
        String returnCharacter = "r";
        Assert.assertSame("Test failed with operation: " + Operation.RETURN,
                Operation.getOperation(returnCharacter), Operation.RETURN);
    }

    @Test(expected = RuntimeException.class)
    public void getOperation_NotOK() {
        String operationCharacter = "g";
        Operation.getOperation(operationCharacter);
    }
}
