package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationStrategyTest {
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;
    private static OperationStrategy operationStrategy;

    @BeforeAll
    public static void beforeAll() {
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationStrategy = new OperationStrategy(operationHandlerMap);
    }

    @Test
    void validBalanceOperationInput_ok() {
        Class<BalanceOperation> expected = BalanceOperation.class;
        Class<? extends OperationHandler> actual
                = operationStrategy.get(FruitTransaction.Operation.BALANCE).getClass();
        assertEquals(expected, actual);
    }

    @Test
    void validSupplyOperationInput_ok() {
        Class<SupplyHandler> expected = SupplyHandler.class;
        Class<? extends OperationHandler> actual
                = operationStrategy.get(FruitTransaction.Operation.SUPPLY).getClass();
        assertEquals(expected, actual);
    }

    @Test
    void validPurchaseOperationInput_ok() {
        Class<PurchaseHandler> expected = PurchaseHandler.class;
        Class<? extends OperationHandler> actual
                = operationStrategy.get(FruitTransaction.Operation.PURCHASE).getClass();
        assertEquals(expected, actual);
    }

    @Test
    void validReturnOperationInput_ok() {
        Class<ReturnOperation> expected = ReturnOperation.class;
        Class<? extends OperationHandler> actual
                = operationStrategy.get(FruitTransaction.Operation.RETURN).getClass();
        assertEquals(expected, actual);
    }
}
