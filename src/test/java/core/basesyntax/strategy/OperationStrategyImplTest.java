package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import core.basesyntax.model.FruitTransaction;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationStrategyImplTest {
    private OperationStrategy operationStrategy;
    private Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;

    @BeforeEach
    void setUp() {
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());

        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    void get_validOperation_returnCorrectHandler() {
        OperationHandler operationHandler = operationStrategy
                .get(FruitTransaction.Operation.BALANCE);

        assertEquals(BalanceOperation.class, operationHandler.getClass());
    }

    @Test
    void constructor_nullMap_throwException() {
        assertThrows(RuntimeException.class, () -> {
            new OperationStrategyImpl(null);
        });
    }

    @Test
    void get_nullHandler_throwException() {
        assertThrows(RuntimeException.class, () -> {
            operationStrategy.get(null);
        });
    }

    @Test
    void get_unknownOperation_throwException() {
        assertThrows(RuntimeException.class, () -> {
            operationStrategy.get(FruitTransaction.Operation.PURCHASE);
        });
    }

}
