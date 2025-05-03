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
    private static OperationHandler operationHandler;
    private static OperationStrategy operationStrategy;

    @BeforeAll
    static void beforeAll() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(
                FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        operationHandlerMap.put(
                FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        operationHandlerMap.put(
                FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler());
        operationHandlerMap.put(
                FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        operationStrategy = new OperationStrategy(operationHandlerMap);
        operationHandler = new SupplyOperationHandler();
    }

    @Test
    void getSuppleHandler_Ok() {
        assertEquals(operationHandler.getClass(),
                operationStrategy.get(FruitTransaction.Operation.SUPPLY).getClass());
    }

    @Test
    void getReturnHandler_Ok() {
        operationHandler = new ReturnOperationHandler();
        assertEquals(operationHandler.getClass(),
                operationStrategy.get(FruitTransaction.Operation.RETURN).getClass());
    }

    @Test
    void getPurchaseHandler_Ok() {
        operationHandler = new PurchaseOperationHandler();
        assertEquals(operationHandler.getClass(),
                operationStrategy.get(FruitTransaction.Operation.PURCHASE).getClass());
    }

    @Test
    void getBalanceHandler_Ok() {
        operationHandler = new BalanceOperationHandler();
        assertEquals(operationHandler.getClass(),
                operationStrategy.get(FruitTransaction.Operation.BALANCE).getClass());
    }
}
