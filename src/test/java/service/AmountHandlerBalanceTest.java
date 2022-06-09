package service;

import org.junit.Assert;
import org.junit.Test;

public class AmountHandlerBalanceTest {

    @Test
    public void getAmount() {
        AmountHandlerBalance amountHandlerBalance = new AmountHandlerBalance();
        Assert.assertEquals("Balance handler return wrong value",Integer.valueOf(10),
                amountHandlerBalance.getAmount(10));
    }
}
