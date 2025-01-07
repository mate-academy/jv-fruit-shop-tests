package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private OperationStrategy operationStrategy;
    private Map<FruitTransaction.Operation, OperationHandler> operationHandlers;

    @BeforeEach
    void setUp() {
        operationHandlers = new LinkedHashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        operationStrategy = new OperationStrategyImpl(operationHandlers);
    }

    @Test
    void getHandler_ValidData_Ok() {
        List<OperationHandler> result = List.of(
                operationStrategy.getHandler(FruitTransaction.Operation.BALANCE),
                operationStrategy.getHandler(FruitTransaction.Operation.SUPPLY),
                operationStrategy.getHandler(FruitTransaction.Operation.PURCHASE),
                operationStrategy.getHandler(FruitTransaction.Operation.RETURN)
        );
        List<OperationHandler> expected = new ArrayList<>(operationHandlers.values());
        assertEquals(expected, result);
    }

    @Test
    void getHandler_NotExistingOperation_Ok() {
        operationHandlers.remove(FruitTransaction.Operation.BALANCE);
        assertNull(operationStrategy.getHandler(FruitTransaction.Operation.BALANCE));
    }
}
