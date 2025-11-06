package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class OperationStrategyImplTest {
    @Test
    void getHandler_returnsCorrectHandler() {
        Map<FruitTransaction.Operation, OperationHandler> map = new HashMap<>();
        map.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        OperationStrategy strategy = new OperationStrategyImpl(map);

        assertTrue(strategy.getHandler(FruitTransaction.Operation.SUPPLY)
                instanceof SupplyOperation);
    }

    @Test
    void getHandler_invalidOperation_returnsNull() {
        OperationStrategy strategy = new OperationStrategyImpl(Map.of());

        OperationHandler actual = strategy.getHandler(FruitTransaction.Operation.BALANCE);

        assertNull(actual);
    }
}
