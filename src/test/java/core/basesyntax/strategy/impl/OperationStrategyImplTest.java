package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationStrategyImplTest {
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        operationStrategy = new OperationStrategyImpl(
                Map.of(FruitTransaction.Operation.BALANCE, new BalanceOperation()));
    }

    @Test
    void getHandler_existingOperation_ok() {
        OperationHandler handler = operationStrategy.getHandler(FruitTransaction.Operation.BALANCE);
        assertEquals(BalanceOperation.class, handler.getClass());
    }

    @Test
    void getHandler_unsupportedOperation_notOk() {
        assertThrows(
                RuntimeException.class,
                () -> operationStrategy.getHandler(FruitTransaction.Operation.PURCHASE)
        );
    }
}
