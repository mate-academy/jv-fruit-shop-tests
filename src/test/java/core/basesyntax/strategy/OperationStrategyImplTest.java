package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.handlerimpl.BalanceOperation;
import core.basesyntax.strategy.handlerimpl.PurchaseOperation;
import core.basesyntax.strategy.handlerimpl.ReturnOperation;
import core.basesyntax.strategy.handlerimpl.SupplyOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;

    @BeforeClass
    public static void setUp() {
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperation());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperation());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperation());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperation());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    public void get_balanceOperationHandler_ok() {
        OperationHandler expectedHandler = new BalanceOperation();
        OperationHandler actualHandler = operationStrategy.get(FruitTransaction.Operation.BALANCE);
        Assertions.assertEquals(expectedHandler.getClass(), actualHandler.getClass());
    }

    @Test
    public void get_supplyOperationHandler_ok() {
        OperationHandler expectedHandler = new SupplyOperation();
        OperationHandler actualHandler = operationStrategy.get(FruitTransaction.Operation.SUPPLY);
        Assertions.assertEquals(expectedHandler.getClass(), actualHandler.getClass());
    }

    @Test
    public void get_purchaseOperationHandler_ok() {
        OperationHandler expectedHandler = new PurchaseOperation();
        OperationHandler actualHandler = operationStrategy.get(FruitTransaction.Operation.PURCHASE);
        Assertions.assertEquals(expectedHandler.getClass(), actualHandler.getClass());
    }

    @Test
    public void get_returnOperationHandler_ok() {
        OperationHandler expectedHandler = new ReturnOperation();
        OperationHandler actualHandler = operationStrategy.get(FruitTransaction.Operation.RETURN);
        Assertions.assertEquals(expectedHandler.getClass(), actualHandler.getClass());
    }
}
