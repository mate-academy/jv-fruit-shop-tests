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

        Assert.assertEquals("BALANCE handler return wrong value",Integer.valueOf(10),
                amountStrategy.get(Operation.BALANCE).getAmount(10));
        Assert.assertEquals("RETURN handler return wrong value",Integer.valueOf(10),
                amountStrategy.get(Operation.RETURN).getAmount(10));
        Assert.assertEquals("SUPPLY handler return wrong value",Integer.valueOf(10),
                amountStrategy.get(Operation.SUPPLY).getAmount(10));
        Assert.assertEquals("PURCHASE handler return wrong value",Integer.valueOf(-10),
                amountStrategy.get(Operation.PURCHASE).getAmount(10));
    }

}
