package service;

import org.junit.Assert;
import org.junit.Test;

public class AmountHandlerBalanceTest {

    @Test
    public void getAmount() {
        AmountHandlerBalance amountHandlerBalance = new AmountHandlerBalance();
        Assert.assertEquals("",(int)10,(int)amountHandlerBalance.getAmount(10));
    }
}
