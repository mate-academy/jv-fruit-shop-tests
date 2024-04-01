package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.BalanceHandler;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationStrategyImplTest {
    private Map<FruitTransaction.Operation, OperationHandler> strategy = new HashMap<>();
    private OperationStrategyImpl operationStrategy = new OperationStrategyImpl(strategy);

    @BeforeEach
    void setUp() {
        strategy.clear();
    }

    @Test
    void get_validData_ok() {
        strategy.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        assertEquals(BalanceHandler.class, operationStrategy
                .get(FruitTransaction.Operation.BALANCE).getClass());
    }

    @Test
    void get_dataNull_InvalidDataException() {
        assertThrows(InvalidDataException.class, () ->
                operationStrategy.get(null), "Operation is not exist");
    }
}
