package core.basesyntax.strategyimpl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;
    private static final OperationHandler SUPPLY_HANDLER = new SupplyOperationHandler();

    @Before
    public void setUp() {
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, SUPPLY_HANDLER);
    }

    @AfterClass
    public static void afterClass() {
        operationHandlerMap.clear();
    }

    @Test
    public void get_validParams_ok() {
        OperationHandler expected = SUPPLY_HANDLER;
        OperationHandler actual = operationHandlerMap.get(FruitTransaction.Operation.SUPPLY);
        assertEquals(expected, actual);
    }

    @Test
    public void get_nullValue_ok() {
        OperationHandler expected = null;
        OperationHandler actual = operationHandlerMap.get(null);
        assertEquals(expected, actual);

    }
}
