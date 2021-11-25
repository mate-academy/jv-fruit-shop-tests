package core.basesyntax.modeltests;

import core.basesyntax.model.OperationType;
import org.junit.Assert;
import org.junit.Test;

public class OperationTypeTest {
    @Test
    public void getOperationType_getTypeFromEnum_ok() {
        Assert.assertEquals("b", OperationType.BALANCE.getType());
        Assert.assertEquals("s", OperationType.SUPPLY.getType());
        Assert.assertEquals("p", OperationType.PURCHASE.getType());
        Assert.assertEquals("r", OperationType.RETURN.getType());
    }
}
