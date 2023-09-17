package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationStrategyTest {
    private static OperationHandler expectedOperationHandler;
    private static OperationStrategy operationStrategy;

    @BeforeAll
    static void beforeAll() {
        Map<FruitTransaction.Operation, OperationHandler>
                operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(
                FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        operationHandlerMap.put(
                FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        operationHandlerMap.put(
                FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler());
        operationHandlerMap.put(
                FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());

        operationStrategy = new OperationStrategy(operationHandlerMap);
    }

    @Test
    void getBalanceHandler_Ok() {
        expectedOperationHandler = new BalanceOperationHandler();
        assertEquals(expectedOperationHandler.getClass(),
                operationStrategy.get(FruitTransaction.Operation.BALANCE).getClass());
    }

    @Test
    void getPurchaseHandler_Ok() {
        expectedOperationHandler = new PurchaseOperationHandler();
        assertEquals(expectedOperationHandler.getClass(),
                operationStrategy.get(FruitTransaction.Operation.PURCHASE).getClass());
    }

    @Test
    void getSupplyHandler_Ok() {
        expectedOperationHandler = new SupplyOperationHandler();
        assertEquals(expectedOperationHandler.getClass(),
                operationStrategy.get(FruitTransaction.Operation.SUPPLY).getClass());
    }

    @Test
    void getReturnHandler_Ok() {
        expectedOperationHandler = new ReturnOperationHandler();
        assertEquals(expectedOperationHandler.getClass(),
                operationStrategy.get(FruitTransaction.Operation.RETURN).getClass());
    }
}
