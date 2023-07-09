package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.Main;
import core.basesyntax.model.Operation;
import core.basesyntax.service.strategy.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static Map<Operation, OperationHandler> operationsMap = new HashMap<>();

    @Before
    public void beforeAll() {
        operationsMap = Main.initOperationsMap();
    }

    @Test
    public void getHandler_getAllHandlers_ok() {
        assertEquals(operationsMap.get(Operation.BALANCE),
                new OperationStrategyImpl(operationsMap)
                        .getHandler(Operation.BALANCE));
        assertEquals(operationsMap.get(Operation.SUPPLY),
                new OperationStrategyImpl(operationsMap)
                        .getHandler(Operation.SUPPLY));
        assertEquals(operationsMap.get(Operation.PURCHASE),
                new OperationStrategyImpl(operationsMap)
                        .getHandler(Operation.PURCHASE));
        assertEquals(operationsMap.get(Operation.RETURN),
                new OperationStrategyImpl(operationsMap)
                        .getHandler(Operation.RETURN));
    }
}
