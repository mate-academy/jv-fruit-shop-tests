package core.basesyntax.operationstrategy;

import static org.junit.Assert.assertSame;

import core.basesyntax.FruitTransaction.Operation;
import core.basesyntax.operation.handler.OperationHandler;
import core.basesyntax.operation.operationstrategy.OperationStrategyImpl;
import java.util.EnumMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class OperationStrategyImplTest {
    private OperationHandler balanceHandler;
    private OperationHandler supplyHandler;
    private OperationStrategyImpl strategy;

    @Before
    public void setUp() {
        balanceHandler = (fruit, quantity) -> {
        };
        supplyHandler = (fruit, quantity) -> {
        };

        Map<Operation, OperationHandler> handlers = new EnumMap<>(Operation.class);
        handlers.put(Operation.BALANCE, balanceHandler);
        handlers.put(Operation.SUPPLY, supplyHandler);

        strategy = new OperationStrategyImpl(handlers);
    }

    @Test
    public void getHandler_validBalanceOperation_returnsCorrectHandler() {
        OperationHandler actual = strategy.getHandler(Operation.BALANCE);
        assertSame(balanceHandler, actual);
    }

    @Test
    public void getHandler_validSupplyOperation_returnsCorrectHandler() {
        OperationHandler actual = strategy.getHandler(Operation.SUPPLY);
        assertSame(supplyHandler, actual);
    }

    @Test(expected = RuntimeException.class)
    public void getHandler_nullOperation_throwsException() {
        strategy.getHandler(null);
    }
}
