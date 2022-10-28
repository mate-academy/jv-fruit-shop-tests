package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import core.basesyntax.model.Operation;
import core.basesyntax.strategy.impl.OperationHandlerBalance;
import core.basesyntax.strategy.impl.OperationHandlerPurchase;
import core.basesyntax.strategy.impl.OperationHandlerReturn;
import core.basesyntax.strategy.impl.OperationHandlerSupply;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static final String BALANCE_OPERATION = "b";
    private static final String PURCHASE_OPERATION = "p";
    private static final String SUPPLY_OPERATION = "s";
    private static final String RETURN_OPERATION = "r";

    private static OperationStrategy operationStrategy;
    private static Map<Operation, OperationHandler> strategyMap;

    @BeforeClass
    public static void beforeClass() {
        strategyMap = new HashMap<>();
        strategyMap.put(Operation.BALANCE, new OperationHandlerBalance());
        strategyMap.put(Operation.PURCHASE, new OperationHandlerPurchase());
        strategyMap.put(Operation.SUPPLY, new OperationHandlerSupply());
        strategyMap.put(Operation.RETURN, new OperationHandlerReturn());
        operationStrategy = new OperationStrategyImpl();
    }

    @Before
    public void setUp() {
        operationStrategy.provideStrategyList(strategyMap);
    }

    @Test
    public void provideEmptyStrategyMap_notOk() {
        operationStrategy.provideStrategyList(new HashMap<>());
        OperationHandler actual = operationStrategy.get(RETURN_OPERATION);
        assertNull(actual);
    }

    @Test(expected = RuntimeException.class)
    public void notExistingOperation_notOk() {
        OperationHandler actual = operationStrategy.get("a");
    }

    @Test
    public void getReturnHandler_ok() {
        OperationHandler actual = operationStrategy.get(RETURN_OPERATION);
        OperationHandler expected = new OperationHandlerReturn();
        assertEquals(actual.getClass(), expected.getClass());
    }

    @Test
    public void getBalanceHandler_ok() {
        OperationHandler actual = operationStrategy.get(BALANCE_OPERATION);
        OperationHandler expected = new OperationHandlerBalance();
        assertEquals(actual.getClass(), expected.getClass());
    }

    @Test
    public void getPurchaseHandler_ok() {
        OperationHandler actual = operationStrategy.get(PURCHASE_OPERATION);
        OperationHandler expected = new OperationHandlerPurchase();
        assertEquals(actual.getClass(), expected.getClass());
    }

    @Test
    public void getSupplyHandler_ok() {
        OperationHandler actual = operationStrategy.get(SUPPLY_OPERATION);
        OperationHandler expected = new OperationHandlerSupply();
        assertEquals(actual.getClass(), expected.getClass());
    }
}
