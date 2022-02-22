package core.basesyntax.service.strategy;

import static core.basesyntax.db.Storage.storage;
import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.DataProcessorImpl;
import core.basesyntax.service.strategy.impl.BalanceOperationHandler;
import core.basesyntax.service.strategy.impl.OperationStrategyImpl;
import core.basesyntax.service.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.service.strategy.impl.ReturnOperationHandler;
import core.basesyntax.service.strategy.impl.SupplyOperationHandler;
import java.util.HashMap;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyTest {
    private static OperationStrategy operationStrategy;

    @BeforeClass
    public static void beforeClass() {
        HashMap<DataProcessorImpl.OperationType, OperationHandler> strategyMap = new HashMap<>();
        strategyMap.put(DataProcessorImpl.OperationType.BALANCE, new BalanceOperationHandler());
        strategyMap.put(DataProcessorImpl.OperationType.SUPPLY, new SupplyOperationHandler());
        strategyMap.put(DataProcessorImpl.OperationType.PURCHASE, new PurchaseOperationHandler());
        strategyMap.put(DataProcessorImpl.OperationType.RETURN, new ReturnOperationHandler());
        operationStrategy = new OperationStrategyImpl(strategyMap);
    }

    @Test
    public void operationStrategy_get_Ok() {
        OperationHandler expected = new BalanceOperationHandler();
        OperationHandler actual = operationStrategy.get(DataProcessorImpl.OperationType.BALANCE);
        assertEquals(expected.getClass(), actual.getClass());
    }

    @After
    public void tearDown() {
        storage.clear();
    }
}
