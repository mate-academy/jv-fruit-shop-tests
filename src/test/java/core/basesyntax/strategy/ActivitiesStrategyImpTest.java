package core.basesyntax.strategy;

import core.basesyntax.dao.ProductDao;
import core.basesyntax.dao.ProductDaoImp;
import core.basesyntax.model.ProductTransaction;
import core.basesyntax.strategy.activities.ActivitiesHandler;
import core.basesyntax.strategy.activities.AddActivitiesHandler;
import core.basesyntax.strategy.activities.SubstractActivitiesHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ActivitiesStrategyImpTest {
    private static Map<String, Integer> storage;
    private static ActivitiesHandler addActivitiesHandler;
    private static ActivitiesHandler subActivitiesHandler;
    private static ActivitiesStrategy activitiesStrategy;

    @BeforeClass
    public static void beforeClass() {
        ProductDao productDao = new ProductDaoImp(storage);
        addActivitiesHandler = new AddActivitiesHandler(productDao);
        subActivitiesHandler = new SubstractActivitiesHandler(productDao);
        Map<ProductTransaction.Operation, ActivitiesHandler> activitiesHandlerMap = new HashMap<>();
        activitiesHandlerMap.put(ProductTransaction.Operation.BALANCE, addActivitiesHandler);
        activitiesHandlerMap.put(ProductTransaction.Operation.SUPPLY, addActivitiesHandler);
        activitiesHandlerMap.put(ProductTransaction.Operation.PURCHASE, subActivitiesHandler);
        activitiesHandlerMap.put(ProductTransaction.Operation.RETURN, addActivitiesHandler);
        activitiesStrategy = new ActivitiesStrategyImp(activitiesHandlerMap);
    }

    @Test
    public void getSubActivityFromBalance_ok() {
        Assert.assertEquals(activitiesStrategy.get(ProductTransaction.Operation.BALANCE)
                        .getClass(), addActivitiesHandler.getClass());
    }
}
