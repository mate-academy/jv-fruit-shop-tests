package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;

    @BeforeEach
    public void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> operationsMap = new HashMap<>();
        operationsMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        operationsMap.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler());
        operationsMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler());
        operationsMap.put(FruitTransaction.Operation.RETURN, new ReturnHandler());
        operationStrategy = new OperationStrategyImpl(operationsMap);
    }

    @Test
    public void get_getBalanceHandler_Ok() {
        OperationHandler expectedOperationHandler = new BalanceHandler();
        assertEquals(expectedOperationHandler.getClass(),
                operationStrategy.get(FruitTransaction.Operation.BALANCE).getClass());
    }

    @Test
    public void get_getPurchaseHandler_Ok() {
        OperationHandler expectedOperationHandler = new PurchaseHandler();
        assertEquals(expectedOperationHandler.getClass(),
                operationStrategy.get(FruitTransaction.Operation.PURCHASE).getClass());
    }

    @Test
    public void get_getReturnHandler_Ok() {
        OperationHandler expectedOperationHandler = new ReturnHandler();
        assertEquals(expectedOperationHandler.getClass(),
                operationStrategy.get(FruitTransaction.Operation.RETURN).getClass());
    }

    @Test
    public void get_getSupplyHandler_Ok() {
        OperationHandler expectedOperationHandler = new SupplyHandler();
        assertEquals(expectedOperationHandler.getClass(),
                operationStrategy.get(FruitTransaction.Operation.SUPPLY).getClass());
    }
}
