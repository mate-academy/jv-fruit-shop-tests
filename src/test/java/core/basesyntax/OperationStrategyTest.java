package core.basesyntax;

import static core.basesyntax.servise.impl.FruitTransaction.Operation.BALANCE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.servise.impl.FruitTransaction;
import core.basesyntax.strategy.MapOfHandlersForStrategy;
import core.basesyntax.strategy.OperationService;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationStrategyTest {
    private static MapOfHandlersForStrategy emptyMapForStrategy;
    private static OperationStrategy operationStrategy;
    private static OperationService operationServiceForTest;
    private FruitTransaction testTransaction;

    @BeforeAll
    public static void setUp() {
        operationServiceForTest = fruitTransaction -> {};

        emptyMapForStrategy = new MapOfHandlersForStrategy() {
            @Override
            public Map<FruitTransaction.Operation, OperationService> getHandlers() {
                return new HashMap<>();
            }

            @Override
            public void putHandler(FruitTransaction.Operation operation,
                                   OperationService operationService) {
            }

            @Override
            public void removeHandler(FruitTransaction.Operation operation) {
            }
        };

        MapOfHandlersForStrategy mapOfHandlersForTest = new MapOfHandlersForStrategy() {
            private final Map<FruitTransaction.Operation, OperationService> mapOfHandlersForTest =
                    Map.of(BALANCE, operationServiceForTest);

            @Override
            public Map<FruitTransaction.Operation, OperationService> getHandlers() {
                return mapOfHandlersForTest;
            }

            @Override
            public void putHandler(FruitTransaction.Operation operation,
                                   OperationService operationService) {
            }

            @Override
            public void removeHandler(FruitTransaction.Operation operation) {
            }
        };

        operationStrategy = new OperationStrategyImpl(mapOfHandlersForTest);
    }

    @BeforeEach
    public void beforeTest() {
        testTransaction = new FruitTransaction("b", "banana", 100);
    }

    @Test
    public void operationStrategy_MapForStrategyNull_notOk() {
        assertThrows(IllegalArgumentException.class, () -> new OperationStrategyImpl(null));
    }

    @Test
    public void operationStrategy_MapForStrategyEmpty_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> new OperationStrategyImpl(emptyMapForStrategy));
    }

    @Test
    public void operationStrategy_FruitTransactionNull_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> operationStrategy.getOperationHandler(null));
    }

    @Test
    public void operationStrategy_GetHandler_Ok() {
        OperationService actual = operationStrategy.getOperationHandler(testTransaction);
        assertEquals(operationServiceForTest, actual);
    }
}
