package core.basesyntax.service;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;

import core.basesyntax.model.Operation;
import core.basesyntax.service.operation.BalanceOperation;
import core.basesyntax.storage.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OperationsStrategyImplTest {
    @Test
    void shouldReturnHandlerForOperation() {
        Storage storage = new Storage();
        Map<Operation, OperationHandler> map = new HashMap<>();
        BalanceOperation balance = new BalanceOperation(storage);
        map.put(Operation.BALANCE, balance);

        OperationsStrategy strategy = new OperationsStrategyImpl(map);
        assertSame(balance, strategy.getHandler(Operation.BALANCE));
    }

    @Test
    void getHandlerShouldThrowForNull() {
        OperationsStrategy strategy = new OperationsStrategyImpl(
                Map.of(
                        Operation.BALANCE,
                        new BalanceOperation(new Storage())
                )
        );

        assertThrows(RuntimeException.class, () -> strategy.getHandler(null));
    }
}
