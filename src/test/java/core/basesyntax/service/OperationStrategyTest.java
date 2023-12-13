package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.operation.BalanceOperationHandler;
import core.basesyntax.strategy.operation.OperationHandler;
import core.basesyntax.strategy.operation.PurchaseOperationHandler;
import core.basesyntax.strategy.operation.ReturnOperationHandler;
import core.basesyntax.strategy.operation.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class OperationStrategyTest {
    private static OperationStrategy operationStrategy;
    private static OperationHandler balanceHandler;

    @BeforeAll
    static void beforeAll() {
        Map<FruitTransaction.Operation, OperationHandler>
                operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler());
        operationStrategy =
                new OperationStrategyImpl(operationHandlerMap);
        balanceHandler = operationHandlerMap.get(FruitTransaction.Operation.BALANCE);
    }

    @Test
    void name() {
        OperationHandler actualOperationHandler = operationStrategy
                .getOperationHandler(FruitTransaction.Operation.BALANCE);
        assertEquals(balanceHandler, actualOperationHandler);
    }
}

