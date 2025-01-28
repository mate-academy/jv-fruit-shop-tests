package core.basesyntax.transactions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

class OperationStrategyImplTest {
    private Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    void getOperation_GetNull_NotOk() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                operationStrategy.getOperation(null));
    }

    @Test
    void getOperation_GetValidOperation_Ok() {
        assertEquals(operationStrategy.getOperation(FruitTransaction.Operation.PURCHASE),
                operationHandlerMap.get(FruitTransaction.Operation.PURCHASE));
        assertEquals(operationStrategy.getOperation(FruitTransaction.Operation.BALANCE),
                operationHandlerMap.get(FruitTransaction.Operation.BALANCE));
        assertEquals(operationStrategy.getOperation(FruitTransaction.Operation.SUPPLY),
                operationHandlerMap.get(FruitTransaction.Operation.SUPPLY));
        assertEquals(operationStrategy.getOperation(FruitTransaction.Operation.RETURN),
                operationHandlerMap.get(FruitTransaction.Operation.RETURN));
    }
}