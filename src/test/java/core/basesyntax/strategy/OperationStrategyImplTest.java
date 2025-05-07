package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import core.basesyntax.model.FruitOperation;
import core.basesyntax.strategy.operation.OperationHandler;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {

    private OperationStrategyImpl operationStrategy;

    @BeforeEach
    void setUp() {
        Map<FruitOperation.Operation, OperationHandler> handlers = Map.of(
                FruitOperation.Operation.PURCHASE, (oldQty, change) -> oldQty - change,
                FruitOperation.Operation.SUPPLY, (oldQty, change) -> oldQty + change,
                FruitOperation.Operation.RETURN, (oldQty, change) -> oldQty + change
        );

        operationStrategy = new OperationStrategyImpl(handlers);
    }

    @Test
    void get_purchaseHandler_ok() {
        OperationHandler handler = operationStrategy.get(FruitOperation.Operation.PURCHASE);
        int result = handler.getQuantityFromStore(50, 10);
        assertEquals(40, result);
    }

    @Test
    void get_supplyHandler_ok() {
        OperationHandler handler = operationStrategy.get(FruitOperation.Operation.SUPPLY);
        int result = handler.getQuantityFromStore(20, 15);
        assertEquals(35, result);
    }

    @Test
    void get_returnHandler_ok() {
        OperationHandler handler = operationStrategy.get(FruitOperation.Operation.RETURN);
        int result = handler.getQuantityFromStore(30, 5);
        assertEquals(35, result);
    }

    @Test
    void get_missingHandler_returnsNull() {
        OperationHandler handler = operationStrategy.get(FruitOperation.Operation.BALANCE);
        assertNull(handler, "Handler for BALANCE should be null if not provided in map");
    }

}
