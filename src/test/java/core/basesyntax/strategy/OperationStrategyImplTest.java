package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class OperationStrategyImplTest {
    @Test
    void getHandler_valid_ok() {
        Map<FruitTransaction.Operation, OperationHandler> map = new HashMap<>();
        map.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());

        OperationStrategy strategy = new OperationStrategyImpl(map);

        assertNotNull(strategy.getHandler(FruitTransaction.Operation.BALANCE));
    }

    @Test
    void getHandler_missing_exception() {
        OperationStrategy strategy = new OperationStrategyImpl(new HashMap<>());

        assertThrows(IllegalArgumentException.class,
                () -> strategy.getHandler(FruitTransaction.Operation.RETURN));
    }
}
