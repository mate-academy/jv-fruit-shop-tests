package service;

import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class OperationHandlerStrategyImplTest {

    @Test
    public void testGet() {
        Map<Operation, AmountHandler> amountHandlerMap = new HashMap<Operation, AmountHandler>();
        amountHandlerMap.put(Operation.BALANCE,new AmountHandlerBalance());
        amountHandlerMap.put(Operation.SUPPLY,new AmountHandlerSupply());
        amountHandlerMap.put(Operation.PURCHASE,new AmountHandlerPurchase());
        amountHandlerMap.put(Operation.RETURN,new AmountHandlerReturn());

        OperationHandlerStrategyImpl amountStrategy =
                new OperationHandlerStrategyImpl(amountHandlerMap);

        Assert.assertEquals("",(int)10,
                (int)amountStrategy.get(Operation.BALANCE).getAmount(10));
        Assert.assertEquals("",(int)10,
                (int)amountStrategy.get(Operation.RETURN).getAmount(10));
        Assert.assertEquals("",(int)10,
                (int)amountStrategy.get(Operation.SUPPLY).getAmount(10));
        Assert.assertEquals("",(int)-10,
                (int)amountStrategy.get(Operation.PURCHASE).getAmount(10));
    }

}
