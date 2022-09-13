package core.basesyntax.strategy.impl;

import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OperationStrategyImplTest {
    private OperationStrategy operationStrategy;
    private Map<String, OperationHandler> map;

    @Before
    public void setUp() {
        map = new HashMap<>();
        map.put("b", new BalanceOperationHandler(new FruitStorageDaoImpl()));
        map.put("s", new SupplyOperationHandler(new FruitStorageDaoImpl()));
        map.put("p", new PurchaseOperationHandler(new FruitStorageDaoImpl()));
        map.put("r", new ReturnOperationHandler(new FruitStorageDaoImpl()));
        operationStrategy = new OperationStrategyImpl(map);
    }

    @Test
    public void getByOperation_operations_OK() {
        String[] operations = new String[]{"b", "s", "p", "r"};
        for (String operation: operations) {
            OperationHandler actual = operationStrategy.getByOperation(operation);
            Assert.assertEquals(map.get(operation).getClass(), actual.getClass());
        }
    }

    @Test(expected = RuntimeException.class)
    public void getByOperation_operationsNull_notOK() {
        operationStrategy.getByOperation(null);
    }
}
