package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.exception.NullOperationException;
import core.basesyntax.models.FruitTransaction;
import core.basesyntax.service.operation.BalanceHandler;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.OperationHandlerMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class StrategyImplTest {
    private static Strategy strategy;

    @BeforeAll
    static void beforeAll() {
        strategy = new StrategyImpl(OperationHandlerMap.operationHandlerMap);
    }

    @Test
    public void get_handlerForNullOperation_notOk() {
        assertThrows(NullOperationException.class, () ->
                strategy.get(null));
    }

    @Test
    public void get_handlerForValidOperation_notOk() {
        OperationHandler actual = strategy.get(FruitTransaction.Operation.BALANCE);
        OperationHandler expected = new BalanceHandler();
        assertEquals(expected.getClass(), actual.getClass());
    }
}
