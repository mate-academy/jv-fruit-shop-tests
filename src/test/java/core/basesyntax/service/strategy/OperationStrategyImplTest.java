package core.basesyntax.service.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.strategy.handler.OperationHandler;
import core.basesyntax.service.strategy.handler.impl.SupplyOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {

    @Test
    void return_valid_OperationHandler_Ok() {
        OperationHandler expectedHandler = new SupplyOperation();

        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.SUPPLY, expectedHandler);

        OperationStrategy strategy = new OperationStrategyImpl(handlers);

        OperationHandler actualHandler =
                strategy.operationHandler(FruitTransaction.Operation.SUPPLY);

        assertSame(expectedHandler, actualHandler);
    }

    @Test
    void operationHandler_nullMap_NotOk() {
        OperationStrategy strategy = new OperationStrategyImpl(null);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> strategy.operationHandler(FruitTransaction.Operation.SUPPLY)
        );

        assertEquals("input data is null", exception.getMessage());
    }

    @Test
    void operationHandler_operationNotFound_returnNull() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();

        OperationStrategy strategy = new OperationStrategyImpl(handlers);

        assertThrows(
                RuntimeException.class,
                () -> strategy.operationHandler(FruitTransaction.Operation.SUPPLY)
        );
    }
}
