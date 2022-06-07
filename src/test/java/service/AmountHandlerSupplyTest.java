package service;

import org.junit.Assert;
import org.junit.Test;

public class AmountHandlerSupplyTest {

    @Test
    public void getAmount() {
        AmountHandlerSupply amountHandlerSupply = new AmountHandlerSupply();
        Assert.assertEquals("",(int)10,(int)amountHandlerSupply.getAmount(10));
    }
}
