package core.basesyntax.shop.service;

import org.junit.Assert;
import org.junit.Test;

public class OperationTest {
    @Test
    public void getOperationCode_returnOperationCodeFromEnum_ok() {
        Assert.assertEquals("b", Operation.BALANCE.getOperationCode());
        Assert.assertEquals("s", Operation.SUPPLY.getOperationCode());
        Assert.assertEquals("p", Operation.PURCHASE.getOperationCode());
        Assert.assertEquals("r", Operation.RETURN_BACK.getOperationCode());
    }

    @Test
    public void getOperationsString_getOperationCodesAsString_ok() {
        Assert.assertTrue(Operation.getOperationsString().matches("[bspr]{4}"));
    }
}
