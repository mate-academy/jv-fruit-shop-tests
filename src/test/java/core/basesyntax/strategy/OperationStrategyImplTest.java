package core.basesyntax.strategy;

import static core.basesyntax.operation.Operation.BALANCE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.operation.Operation;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.impl.BalanceHandler;
import core.basesyntax.operation.impl.PurchaseHandler;
import core.basesyntax.operation.impl.ReturnHandler;
import core.basesyntax.operation.impl.SupplyHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static final Operation TEST_OPERATION = BALANCE;
    private static Map<Operation, OperationHandler> strategies;
    private OperationStrategy operationStrategy;

    @BeforeClass
    public static void oneTimeSetUp() {
        strategies = new HashMap<>();
        strategies.put(Operation.BALANCE, new BalanceHandler());
        strategies.put(Operation.SUPPLY, new SupplyHandler());
        strategies.put(Operation.PURCHASE, new PurchaseHandler());
        strategies.put(Operation.RETURN, new ReturnHandler());
    }

    @AfterClass
    public static void oneTimeTearDown() {
        strategies = null;
    }

    @Before
    public void setUp() {
        operationStrategy = new OperationStrategyImpl(strategies);
    }

    @Test
     public void getHandler_Work_Ok() {
        OperationHandler actual = new BalanceHandler();
        OperationHandler expected = operationStrategy.get(TEST_OPERATION);
        assertEquals(expected.getClass(), actual.getClass());
    }
}
