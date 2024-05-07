package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.servise.impl.FruitTransaction;
import core.basesyntax.strategy.MapOfHandlersForStrategy;
import core.basesyntax.strategy.OperationService;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import core.basesyntax.testclasses.EmptyMapOfHandlersForTest;
import core.basesyntax.testclasses.MapOfHandlersForTest;
import core.basesyntax.testclasses.OperationServiceForTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationStrategyTest {
    private static MapOfHandlersForStrategy emptyMapForStrategy;
    private static OperationStrategy operationStrategy;
    private FruitTransaction testTransaction;

    @BeforeAll
    public static void setUp() {
        emptyMapForStrategy = new EmptyMapOfHandlersForTest();
        operationStrategy = new OperationStrategyImpl(new MapOfHandlersForTest());
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
        assertEquals(OperationServiceForTest.class, actual.getClass());
    }
}
