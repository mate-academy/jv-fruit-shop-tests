package core.basesyntax;

import core.basesyntax.handler.OperationHandler;
import core.basesyntax.handler.OperationStrategy;
import core.basesyntax.handler.OperationStrategyImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operations.BalanceOperation;
import core.basesyntax.operations.PurchaseOperation;
import core.basesyntax.operations.ReturnOperation;
import core.basesyntax.operations.SupplyOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationStrategyTest {
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationStrategy = new OperationStrategyImpl(handlers);
    }

    @Test
    void operationStrategy_returnsCorrectHandler_ok() {
        OperationHandler handler = operationStrategy.getHandler(FruitTransaction.Operation.SUPPLY);

        Assertions.assertTrue(handler instanceof SupplyOperation,
                "Handler for SUPPLY should be of type SupplyOperation.");
    }

    @Test
    void operationStrategy_empty_notOk() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> operationStrategy.getHandler(null),
                "IllegalArgumentException.");
    }
}
