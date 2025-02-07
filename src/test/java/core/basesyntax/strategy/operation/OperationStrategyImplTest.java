package core.basesyntax.strategy.operation;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private OperationStrategyImpl operationStrategy;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        operationStrategy = new OperationStrategyImpl(handlers);
    }

    @Test
    void getHandler_validOperation_returnsCorrectHandler() {
        OperationHandler handler = operationStrategy.getHandler(
                FruitTransaction.Operation.BALANCE);
        Assertions.assertNotNull(handler);
        Assertions.assertTrue(handler instanceof BalanceOperation);
    }

    @Test
    void getHandler_invalidOperation_throwsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                operationStrategy.getHandler(null)
        );
    }
}
