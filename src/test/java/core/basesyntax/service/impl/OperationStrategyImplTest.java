package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperation;
import core.basesyntax.service.operation.ReturnOperation;
import core.basesyntax.service.operation.SupplyOperation;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlers;
    private OperationStrategy strategy;

    @BeforeAll
    static void beforeAll() {
        operationHandlers = Map.of(
                FruitTransaction.Operation.BALANCE, new PurchaseOperation(),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperation(),
                FruitTransaction.Operation.RETURN, new ReturnOperation(),
                FruitTransaction.Operation.SUPPLY, new SupplyOperation()
        );
    }

    @Test
    void map_NotContainsKey_NotOk() {
        FruitTransaction.Operation operation = null;
        strategy = new OperationStrategyImpl(operationHandlers);
        assertThrows(RuntimeException.class, () -> strategy.get(operation));

    }
}
