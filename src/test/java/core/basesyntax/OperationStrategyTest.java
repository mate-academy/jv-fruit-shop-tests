package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.model.Operation;
import core.basesyntax.service.operation.BalanceOperationHandler;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class OperationStrategyTest {
    private static final String NOT_CORRECT_OPERATION_TYPE = "c";
    private final Map<Operation, OperationHandler> operationMap = new HashMap<>();
    private final OperationStrategy
            operationStrategy = new OperationStrategyImpl(operationMap);

    @Test
    public void getOperation_correctType_Ok() {
        operationMap.put(Operation.BALANCE, new BalanceOperationHandler());
        Map<Operation, OperationHandler>
                expected = Map.of(Operation.BALANCE, operationStrategy.get(Operation.BALANCE));
        assertEquals(expected, operationMap);
    }

    @Test
    public void getOperation_notCorrectType_notOk() {
        assertThrows(RuntimeException.class, () -> {
            operationStrategy.get(Operation.valueOf(NOT_CORRECT_OPERATION_TYPE));
        });
    }

    @Test
    public void getOperation_NullData_notOk() {
        assertThrows(RuntimeException.class, () -> {
            operationStrategy.get(Operation.valueOf(null));
        });
    }
}
