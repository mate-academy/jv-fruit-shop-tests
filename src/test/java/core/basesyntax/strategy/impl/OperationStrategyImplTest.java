package core.basesyntax.strategy.impl;

import core.basesyntax.model.TransactionDto;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.handler.OperationHandler;
import core.basesyntax.strategy.handler.impl.BalanceOperationHandler;
import core.basesyntax.strategy.handler.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.handler.impl.ReturnOperationHandler;
import core.basesyntax.strategy.handler.impl.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OperationStrategyImplTest {
    private Map<TransactionDto.Operation, OperationHandler> operationHandlerMap;
    private OperationStrategy operationStrategy;

    @Before
    public void setUp() {
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(TransactionDto.Operation.BALANCE, new BalanceOperationHandler());
        operationHandlerMap.put(TransactionDto.Operation.RETURN, new ReturnOperationHandler());
        operationHandlerMap.put(TransactionDto.Operation.SUPPLY, new SupplyOperationHandler());
        operationHandlerMap.put(TransactionDto.Operation.PURCHASE, new PurchaseOperationHandler());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    public void balance_OperationHandler_ok() {
        OperationHandler actual = operationStrategy.get(TransactionDto.Operation.BALANCE);
        Assert.assertEquals(BalanceOperationHandler.class, actual.getClass());
    }

    @Test
    public void return_OperationHandler_ok() {
        OperationHandler actual = operationStrategy.get(TransactionDto.Operation.RETURN);
        Assert.assertEquals(ReturnOperationHandler.class, actual.getClass());
    }

    @Test
    public void supply_OperationHandler_ok() {
        OperationHandler actual = operationStrategy.get(TransactionDto.Operation.SUPPLY);
        Assert.assertEquals(SupplyOperationHandler.class, actual.getClass());
    }

    @Test
    public void purchase_OperationHandler_ok() {
        OperationHandler actual = operationStrategy.get(TransactionDto.Operation.PURCHASE);
        Assert.assertEquals(PurchaseOperationHandler.class, actual.getClass());
    }
}
