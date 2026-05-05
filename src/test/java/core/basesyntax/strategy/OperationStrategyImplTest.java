package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationStrategyImplTest {
    private Map<FruitTransaction.Operation, OperationHandler> map;
    private OperationStrategy strategy;

    @BeforeEach
    void setUp() {
        map = new HashMap<>();
        map.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        strategy = new OperationStrategyImpl(map);
    }

    @Test
    void getHandler_shouldReturnCorrectHandler() {
        OperationHandler actual = strategy.getHandler(FruitTransaction.Operation.SUPPLY);
        assertEquals(map.get(FruitTransaction.Operation.SUPPLY), actual);
    }
}
