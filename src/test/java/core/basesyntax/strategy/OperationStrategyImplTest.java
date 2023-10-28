package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private OperationStrategy operationHandlerStrategy;

    @BeforeEach
    public void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> operationStrategies = new HashMap<>();
        operationStrategies.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        operationHandlerStrategy = new OperationStrategyImpl(operationStrategies);
    }

    @Test
    public void getHandler_validOperation_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);

        OperationHandler handler = operationHandlerStrategy
                .getHandler(fruitTransaction.getOperation());

        Assertions.assertNotNull(handler);
        Assertions.assertTrue(handler instanceof BalanceOperationHandler);
    }

    @Test
    public void getHandler_invalidOperation_NotOk() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);

        OperationHandler handler = operationHandlerStrategy
                .getHandler(fruitTransaction.getOperation());

        Assertions.assertNull(handler);
    }
}
