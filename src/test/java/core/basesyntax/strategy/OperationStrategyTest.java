package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyTest {
    private static OperationStrategy operationStrategy;
    private static OperationHandler expectedOperationHandler;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> operationsMap = new HashMap<>();
        operationsMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        operationsMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        operationsMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler());
        operationsMap.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        operationStrategy = new OperationStrategy(operationsMap);
    }

    @Test
    public void get_getBalanceHandler_Ok() {
        expectedOperationHandler = new BalanceOperationHandler();
        assertEquals(expectedOperationHandler.getClass(),
                operationStrategy.get(FruitTransaction.Operation.BALANCE).getClass());
    }

    @Test
    public void get_getPurchaseHandler_Ok() {
        expectedOperationHandler = new PurchaseOperationHandler();
        assertEquals(expectedOperationHandler.getClass(),
                operationStrategy.get(FruitTransaction.Operation.PURCHASE).getClass());
    }

    @Test
    public void get_getReturnHandler_Ok() {
        expectedOperationHandler = new ReturnOperationHandler();
        assertEquals(expectedOperationHandler.getClass(),
                operationStrategy.get(FruitTransaction.Operation.RETURN).getClass());
    }

    @Test
    public void get_getSupplyHandler_Ok() {
        expectedOperationHandler = new SupplyOperationHandler();
        assertEquals(expectedOperationHandler.getClass(),
                operationStrategy.get(FruitTransaction.Operation.SUPPLY).getClass());
    }

}
