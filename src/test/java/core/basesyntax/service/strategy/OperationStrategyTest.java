package core.basesyntax.service.strategy;

import static core.basesyntax.db.Storage.storage;
import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.DataProcessorImpl;
import core.basesyntax.service.strategy.impl.MinusOperationHandler;
import core.basesyntax.service.strategy.impl.OperationStrategyImpl;
import core.basesyntax.service.strategy.impl.PlusOperationHandler;
import java.util.HashMap;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyTest {
    private static OperationStrategy operationStrategy;

    @BeforeClass
    public static void beforeClass() {
        HashMap<DataProcessorImpl.OperationType, OperationHandler> strategyMap = new HashMap<>();
        strategyMap.put(DataProcessorImpl.OperationType.BALANCE, new PlusOperationHandler());
        strategyMap.put(DataProcessorImpl.OperationType.SUPPLY, new PlusOperationHandler());
        strategyMap.put(DataProcessorImpl.OperationType.PURCHASE, new MinusOperationHandler());
        strategyMap.put(DataProcessorImpl.OperationType.RETURN, new PlusOperationHandler());
        operationStrategy = new OperationStrategyImpl(strategyMap);
    }

    @Test
    public void operationStrategy_get_Ok() {
        OperationHandler expected = new PlusOperationHandler();
        OperationHandler actual = operationStrategy.get(DataProcessorImpl.OperationType.BALANCE);
        assertEquals(expected.getClass(), actual.getClass());
    }

    @After
    public void tearDown() {
        storage.clear();
    }
}
