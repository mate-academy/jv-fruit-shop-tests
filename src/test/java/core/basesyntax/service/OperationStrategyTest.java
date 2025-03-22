package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.operations.BalanceOperation;
import core.basesyntax.service.operations.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OperationStrategyTest {

    @Test
    void getOperationHandler() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        OperationStrategy strategy = new OperationStrategyImpl(operationHandlers);

        Class<? extends OperationHandler> actual = strategy.getOperationHandler(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 20)).getClass();

        Class<? extends OperationHandler> expected = new BalanceOperation().getClass();

        assertEquals(actual, expected);
    }
}
