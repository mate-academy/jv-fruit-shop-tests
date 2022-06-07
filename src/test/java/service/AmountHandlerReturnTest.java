package service;

import org.junit.Assert;
import org.junit.Test;

public class AmountHandlerReturnTest {

    @Test
    public void getAmount() {
        AmountHandlerReturn amountHandlerReturn = new AmountHandlerReturn();
        Assert.assertEquals("",(int)10,(int)amountHandlerReturn.getAmount(10));
    }
}
