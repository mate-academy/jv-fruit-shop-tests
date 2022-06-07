package service;

import org.junit.Assert;
import org.junit.Test;

public class AmountHandlerPurchaseTest {

    @Test
    public void getAmount() {
        AmountHandlerPurchase amountHandlerPurchase = new AmountHandlerPurchase();
        Assert.assertEquals("",(int)-10,(int)amountHandlerPurchase.getAmount(10));
    }
}
