package service;

import org.junit.Assert;
import org.junit.Test;

public class AmountHandlerBalanceTest {

    @Test
    public void getAmount() {
        AmountHandlerBalance amountHandlerBalance = new AmountHandlerBalance();
        Assert.assertEquals("Balance value is wrong",Integer.valueOf(10),
                amountHandlerBalance.getAmount(10));
    }
}
