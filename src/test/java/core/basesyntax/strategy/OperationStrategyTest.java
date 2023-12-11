package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.operationhandlers.BalanceOperationHandler;
import core.basesyntax.strategy.operationhandlers.OperationsHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationStrategyTest {
    private Map<FruitTransaction.Operation, OperationsHandler> handlers;
    private OperationStrategy operationStrategy;

    @BeforeEach
    public void setUp() {
        handlers = new HashMap<>();
        BalanceOperationHandler balanceOperationHandler = new BalanceOperationHandler(null);
        handlers.put(FruitTransaction.Operation.BALANCE, balanceOperationHandler);
        operationStrategy = new OperationStrategy(handlers);
    }

    @Test
    public void getHandler_ValidOperation_ReturnsCorrectHandler() {
        FruitTransaction.Operation operation = FruitTransaction.Operation.BALANCE;
        FruitTransaction fruitTransaction = new FruitTransaction(operation, "Apple", 10);

        OperationsHandler expectedHandler = handlers.get(operation);
        OperationsHandler actualHandler = operationStrategy.getHandler(fruitTransaction);

        assertEquals(expectedHandler, actualHandler);
    }
}
