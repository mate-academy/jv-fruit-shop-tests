package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationStrategyImplTest {
    private Map<FruitTransaction.Operation, OperationHandler> handlers;
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        handlers = new HashMap<>();
        operationStrategy = new OperationStrategyImpl(handlers);
    }

    @Test
    void getOperationHandler_existingOperation_returnsHandler() {
        OperationHandler expected = new BalanceOperation();

        handlers.put(
                FruitTransaction.Operation.BALANCE,
                expected
        );

        OperationHandler actual =
                operationStrategy.getOperationHandler(
                        FruitTransaction.Operation.BALANCE
                );

        assertSame(expected, actual);
    }

    @Test
    void getOperationHandler_missingOperation_throwsException() {
        FruitTransaction.Operation operation =
                FruitTransaction.Operation.SUPPLY;

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> operationStrategy.getOperationHandler(
                        FruitTransaction.Operation.SUPPLY
                )
        );

        assertEquals(
                "No handler for operation: " + operation,
                exception.getMessage()
        );
    }
}
