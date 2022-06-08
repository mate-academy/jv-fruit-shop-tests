package service;

import org.junit.Assert;
import org.junit.Test;

public class AmountHandlerPurchaseTest {

    @Test
    public void getAmount() {
        AmountHandlerPurchase amountHandlerPurchase = new AmountHandlerPurchase();
        Assert.assertEquals("",Integer.valueOf(-10),amountHandlerPurchase.getAmount(10));
    }
}
