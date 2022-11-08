package core.basesyntax.service.strategy;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OperationStrategyImplTest {
    private OperationStrategy strategy;

    @Before
    public void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        strategy = new OperationStrategyImpl(operationHandlerMap);
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler());
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler());
    }

    @Test
    public void operationSupply_correctData_ok() {
        Class<? extends OperationHandler> actual = strategy
                .get(FruitTransaction.Operation.SUPPLY).getClass();
        Assert.assertEquals(SupplyHandler.class, actual);
    }

    @Test
    public void operationBalance_correctData_ok() {
        Class<? extends OperationHandler> actual = strategy
                .get(FruitTransaction.Operation.BALANCE).getClass();
        Assert.assertEquals(BalanceHandler.class, actual);
    }

    @Test
    public void operationReturn_correctData_Ok() {
        Class<? extends OperationHandler> actual = strategy
                .get(FruitTransaction.Operation.RETURN).getClass();
        Assert.assertEquals(ReturnHandler.class, actual);
    }
}
