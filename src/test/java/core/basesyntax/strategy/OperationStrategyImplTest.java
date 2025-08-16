package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.operation.BalanceHandler;
import core.basesyntax.strategy.operation.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private Map<FruitTransaction.Operation, OperationHandler> map;
    private OperationStrategyImpl strategy;

    @BeforeEach
    void setUp() {
        map = new HashMap<>();
        strategy = new OperationStrategyImpl(map);
    }

    @Test
    void validOperationStrategy_Ok() {
        OperationHandler handler = new BalanceHandler();
        map.put(FruitTransaction.Operation.BALANCE, handler);
        assertEquals(handler, strategy.get(FruitTransaction.Operation.BALANCE));
    }

    @Test
    void nullOperationHandler_NotOk() {
        assertThrows(UnsupportedOperationException.class, () -> {
            strategy.get(FruitTransaction.Operation.PURCHASE);
        });
    }
}
