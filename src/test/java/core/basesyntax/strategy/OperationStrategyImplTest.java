package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.handler.OperationHandler;
import core.basesyntax.handler.impl.BalanceOperationHandler;
import core.basesyntax.handler.impl.PurchaseOperationHandler;
import core.basesyntax.handler.impl.ReturnOperationHandler;
import core.basesyntax.handler.impl.SupplyOperationHandler;
import core.basesyntax.model.Operation;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        Map<Operation, OperationHandler> operationHandlers = Map.of(
                Operation.BALANCE, new BalanceOperationHandler(),
                Operation.SUPPLY, new SupplyOperationHandler(),
                Operation.PURCHASE, new PurchaseOperationHandler(),
                Operation.RETURN, new ReturnOperationHandler()
        );
        operationStrategy = new OperationStrategyImpl(operationHandlers);
    }

    @Test
    void getHandler_existingOperation_ok() {
        OperationHandler handler = operationStrategy.getHandler(Operation.BALANCE);
        assertInstanceOf(BalanceOperationHandler.class, handler);

        handler = operationStrategy.getHandler(Operation.PURCHASE);
        assertInstanceOf(PurchaseOperationHandler.class, handler);
    }

    @Test
    void getHandler_nullOperation_notOk() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> operationStrategy.getHandler(null));
        assertEquals("Operation cannot be null", exception.getMessage());
    }
}
