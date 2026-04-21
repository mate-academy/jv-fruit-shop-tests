package basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.impl.BalanceOperation;
import core.basesyntax.service.impl.OperationStrategyImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {

    @Test
    void shouldThrowException_whenOperationIsNull() {
        Map<FruitTransaction.Operation, OperationHandler> handlers =
                new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());

        OperationStrategyImpl strategy = new OperationStrategyImpl(handlers);

        assertThrows(RuntimeException.class,
                () -> strategy.get(null));
    }

    @Test
    void shouldThrowException_whenHandlersMapIsEmpty() {
        assertThrows(RuntimeException.class,
                () -> new OperationStrategyImpl(new HashMap<>()));
    }
}

