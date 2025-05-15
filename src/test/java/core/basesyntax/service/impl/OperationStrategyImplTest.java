package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.operation.BalanceOperation;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperation;
import core.basesyntax.service.operation.ReturnOperation;
import core.basesyntax.service.operation.SupplyOperation;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlers;
    private static OperationStrategy strategy;

    @BeforeAll
    static void beforeAll() {
        operationHandlers = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation(),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperation(),
                FruitTransaction.Operation.RETURN, new ReturnOperation(),
                FruitTransaction.Operation.SUPPLY, new SupplyOperation()
        );
    }

    @Test
    void map_NotContainsKey_NotOk() {
        operationHandlers = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation());
        FruitTransaction.Operation operation = FruitTransaction.Operation.PURCHASE;
        strategy = new OperationStrategyImpl(operationHandlers);
        assertThrows(IllegalArgumentException.class, () -> strategy.get(operation));
    }

    @Test
    void operationStrategy_ReturnsCorrectHandler_Ok() {
        FruitTransaction.Operation operation = FruitTransaction.Operation.BALANCE;
        strategy = new OperationStrategyImpl(operationHandlers);
        OperationHandler handler = strategy.get(operation);
        assertTrue(handler instanceof BalanceOperation);
    }
}
