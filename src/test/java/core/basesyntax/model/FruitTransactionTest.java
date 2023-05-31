package core.basesyntax.model;

import core.basesyntax.model.FruitTransaction.Operation;
import org.junit.Assert;
import org.junit.Test;

public class FruitTransactionTest {

    @Test
    public void get_CorrectInputDate_Ok() {
        Assert.assertEquals(Operation.BALANCE, Operation.getOperationFromString("b"));
        Assert.assertEquals(Operation.SUPPLY, Operation.getOperationFromString("s"));
        Assert.assertEquals(Operation.RETURN, Operation.getOperationFromString("r"));
        Assert.assertEquals(Operation.PURCHASE, Operation.getOperationFromString("p"));
    }

    @Test
    public void get_IncorrectInputDate_NotOk() {
        Assert.assertEquals(null, Operation.getOperationFromString("o"));
    }

}
