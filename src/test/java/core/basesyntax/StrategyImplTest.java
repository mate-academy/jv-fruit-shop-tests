package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.exception.NullOperationException;
import core.basesyntax.models.FruitTransaction;
import core.basesyntax.service.operation.BalanceHandler;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.OperationHandlerMap;
import core.basesyntax.strategy.Strategy;
import core.basesyntax.strategy.StrategyImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StrategyImplTest {
    private static Strategy strategy;
    private FruitTransaction.Operation operation;

    @BeforeAll
    static void beforeAll() {
        strategy = new StrategyImpl(OperationHandlerMap.operationHandlerMap);
    }

    @BeforeEach
    public void setUp() {
        operation = FruitTransaction.Operation.BALANCE;
    }

    @Test
    public void get_handlerForNullOperation_notOk() {
        operation = null;
        assertThrows(NullOperationException.class, () ->
                strategy.get(operation));
    }

    @Test
    public void get_handlerForValidOperation_notOk() {
        OperationHandler actual = strategy.get(operation);
        OperationHandler expected = new BalanceHandler();
        assertEquals(expected.getClass(), actual.getClass());
    }
}
