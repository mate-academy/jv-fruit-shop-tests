package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.Operation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class OperationStrategyImplTest {
    @Test
    void getHandler_returnsCorrectHandler() {
        Map<Operation, OperationHandler> map = new HashMap<>();
        map.put(Operation.SUPPLY, new SupplyOperation());
        OperationStrategy strategy = new OperationStrategyImpl(map);

        assertTrue(strategy.getHandler(Operation.SUPPLY)
                instanceof SupplyOperation);
    }

    @Test
    void getHandler_invalidOperation_returnsNull() {
        OperationStrategy strategy = new OperationStrategyImpl(Map.of());

        OperationHandler actual = strategy.getHandler(Operation.BALANCE);

        assertNull(actual);
    }
}
