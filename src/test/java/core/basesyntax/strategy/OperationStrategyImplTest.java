package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.handlerimpl.BalanceOperation;
import core.basesyntax.strategy.handlerimpl.PurchaseOperation;
import core.basesyntax.strategy.handlerimpl.ReturnOperation;
import core.basesyntax.strategy.handlerimpl.SupplyOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private OperationStrategy operationStrategy;
    private Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;

    @BeforeEach
    public void setUp() {
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperation());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperation());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperation());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperation());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    public void get_balanceOperationHandler_ok() {
        OperationHandler expectedHandler = new BalanceOperation();
        OperationHandler actualHandler = operationStrategy.get(FruitTransaction.Operation.BALANCE);
        assertEquals(expectedHandler.getClass(), actualHandler.getClass());
    }

    @Test
    public void get_purchaseOperationHandler_ok() {
        OperationHandler expectedHandler = new PurchaseOperation();
        OperationHandler actualHandler = operationStrategy.get(FruitTransaction.Operation.PURCHASE);
        assertEquals(expectedHandler.getClass(), actualHandler.getClass());
    }

    @Test
    public void get_returnOperationHandler_ok() {
        OperationHandler expectedHandler = new ReturnOperation();
        OperationHandler actualHandler = operationStrategy.get(FruitTransaction.Operation.RETURN);
        assertEquals(expectedHandler.getClass(), actualHandler.getClass());
    }

    @Test
    public void get_supplyOperationHandler_ok() {
        OperationHandler expectedHandler = new SupplyOperation();
        OperationHandler actualHandler = operationStrategy.get(FruitTransaction.Operation.SUPPLY);
        assertEquals(expectedHandler.getClass(), actualHandler.getClass());
    }
}
