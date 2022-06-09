package service;

import org.junit.Assert;
import org.junit.Test;

public class AmountHandlerSupplyTest {

    @Test
    public void getAmount() {
        AmountHandlerSupply amountHandlerSupply = new AmountHandlerSupply();
        Assert.assertEquals("Supply handler return wrong value",
                Integer.valueOf(10),amountHandlerSupply.getAmount(10));
    }
}
