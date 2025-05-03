package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseOperationHandler;
import core.basesyntax.strategy.ReturnOperationHandler;
import core.basesyntax.strategy.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class OperationStrategyImplTest {
    private Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;
    private OperationStrategy strategy;

    @Before
    public void setUp() {
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        strategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    public void getBalanceOperationHandler_Ok() {
        OperationHandler actual = strategy.get(FruitTransaction.Operation.BALANCE);
        assertEquals(BalanceOperationHandler.class, actual.getClass());
    }

    @Test
    public void getPurchaseOperationHandler_Ok() {
        OperationHandler actual = strategy.get(FruitTransaction.Operation.PURCHASE);
        assertEquals(PurchaseOperationHandler.class, actual.getClass());
    }

    @Test
    public void getSupplyOperationHandler_Ok() {
        OperationHandler actual = strategy.get(FruitTransaction.Operation.SUPPLY);
        assertEquals(SupplyOperationHandler.class, actual.getClass());
    }

    @Test
    public void getReturnOperationHandler_Ok() {
        OperationHandler actual = strategy.get(FruitTransaction.Operation.RETURN);
        assertEquals(ReturnOperationHandler.class, actual.getClass());
    }
}
