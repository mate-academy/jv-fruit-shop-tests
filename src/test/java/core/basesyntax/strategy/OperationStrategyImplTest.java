package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operations.BalanceOperation;
import core.basesyntax.operations.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private Map<FruitTransaction.Operation, OperationHandler> operationHandler = new HashMap<>();
    private final OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandler);

    @BeforeEach
    void setup() {
        operationHandler.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
    }

    @Test
    void getOperationBalance_isOk() {
        Class<? extends OperationHandler> current =
                operationStrategy.get(FruitTransaction.Operation.BALANCE).getClass();
        assertEquals(BalanceOperation.class, current);
    }
}
