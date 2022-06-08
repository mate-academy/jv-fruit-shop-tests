package service;

import org.junit.Assert;
import org.junit.Test;

public class AmountHandlerReturnTest {

    @Test
    public void getAmount() {
        AmountHandlerReturn amountHandlerReturn = new AmountHandlerReturn();
        Assert.assertEquals("",Integer.valueOf(10),amountHandlerReturn.getAmount(10));
    }
}
