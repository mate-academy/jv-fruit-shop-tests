package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.EnumMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class OperationStrategyImplTest {
    @Test
    public void constructor_nullHandlers_notOk() {
        assertThrows(IllegalArgumentException.class, () -> new OperationStrategyImpl(null));
    }

    @Test
    public void getHandler_existingOperation_ok() {
        OperationHandler handler = transaction -> {
        };
        Map<FruitTransaction.Operation, OperationHandler> handlers = createHandlersMap();
        handlers.put(FruitTransaction.Operation.BALANCE, handler);
        OperationStrategy strategy = new OperationStrategyImpl(handlers);

        assertSame(handler, strategy.getHandler(FruitTransaction.Operation.BALANCE));
    }

    @Test
    public void getHandler_nullOperation_notOk() {
        OperationStrategy strategy = new OperationStrategyImpl(createHandlersMap());

        assertThrows(IllegalArgumentException.class, () -> strategy.getHandler(null));
    }

    @Test
    public void getHandler_missingOperation_notOk() {
        OperationStrategy strategy = new OperationStrategyImpl(createHandlersMap());

        assertThrows(RuntimeException.class,
                () -> strategy.getHandler(FruitTransaction.Operation.BALANCE));
    }

    @Test
    public void getHandler_externalMapChanged_returnsOriginalHandler() {
        OperationHandler originalHandler = transaction -> {
        };
        OperationHandler newHandler = transaction -> {
        };
        Map<FruitTransaction.Operation, OperationHandler> handlers = createHandlersMap();
        handlers.put(FruitTransaction.Operation.BALANCE, originalHandler);
        OperationStrategy strategy = new OperationStrategyImpl(handlers);
        handlers.put(FruitTransaction.Operation.BALANCE, newHandler);

        assertSame(originalHandler, strategy.getHandler(FruitTransaction.Operation.BALANCE));
    }

    private Map<FruitTransaction.Operation, OperationHandler> createHandlersMap() {
        return new EnumMap<>(FruitTransaction.Operation.class);
    }
}
