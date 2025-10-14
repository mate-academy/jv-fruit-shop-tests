package strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import model.FruitTransaction;
import org.junit.jupiter.api.Test;

public class OperationStrategyImplTest {

    @Test
    void getHandler_returnsCorrectHandler() {
        OperationHandler balanceHandler = transaction -> {};
        OperationHandler supplyHandler = transaction -> {};

        Map<FruitTransaction.Operation, OperationHandler> handlers = Map.of(
                FruitTransaction.Operation.BALANCE, balanceHandler,
                FruitTransaction.Operation.SUPPLY, supplyHandler
        );

        OperationStrategyImpl strategy = new OperationStrategyImpl(handlers);

        assertEquals(balanceHandler, strategy.getHandler(FruitTransaction.Operation.BALANCE));
        assertEquals(supplyHandler, strategy.getHandler(FruitTransaction.Operation.SUPPLY));
    }

    @Test
    void getHandlers_returnsUnmodifiableMap() {
        OperationHandler handler = transaction -> {};

        Map<FruitTransaction.Operation, OperationHandler> handlers = Map.of(
                FruitTransaction.Operation.BALANCE, handler
        );

        OperationStrategyImpl strategy = new OperationStrategyImpl(handlers);
        Map<FruitTransaction.Operation, OperationHandler> unmodifiable = strategy.getHandlers();

        assertEquals(handler, unmodifiable.get(FruitTransaction.Operation.BALANCE));
        assertThrows(UnsupportedOperationException.class, () ->
                unmodifiable.put(FruitTransaction.Operation.SUPPLY, handler)
        );
    }
}

