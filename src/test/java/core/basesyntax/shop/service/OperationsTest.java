package core.basesyntax.shop.service;

import org.junit.Assert;
import org.junit.Test;

public class OperationsTest {
    @Test
    public void getOperationCode_returnOperationCodeFromEnum_ok() {
        Assert.assertEquals("b", Operations.BALANCE.getOperationCode());
        Assert.assertEquals("s", Operations.SUPPLY.getOperationCode());
        Assert.assertEquals("p", Operations.PURCHASE.getOperationCode());
        Assert.assertEquals("r", Operations.RETURN_BACK.getOperationCode());
    }

    @Test
    public void getOperationsString_getOperationCodesAsString_ok() {
        Assert.assertTrue(Operations.getOperationsString().matches("[bspr]{4}"));
    }
}
