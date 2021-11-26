package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.TransactionDto;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class StrategyTest {
    private static Strategy strategy;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Map<String, OperationHandler> mapForStrategy = new HashMap<>();
        mapForStrategy.put("b", new AddOperationHandler());
        mapForStrategy.put("r", new AddOperationHandler());
        mapForStrategy.put("s", new AddOperationHandler());
        mapForStrategy.put("p", new SubtractOperationHandler());
        strategy = new Strategy(mapForStrategy);
    }

    @Before
    public void setUp() {
        Storage.storage.clear();
    }

    @Test
    public void balanceOperationTest_Ok() {
        TransactionDto transaction
                = new TransactionDto("b", new Fruit("banana"), 15);
        OperationHandler actual = strategy.getHandler(transaction);
        OperationHandler expected = new AddOperationHandler();
        Assert.assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void returnOperationTest() {
        TransactionDto transaction
                = new TransactionDto("r", new Fruit("banana"), 15);
        OperationHandler actual = strategy.getHandler(transaction);
        OperationHandler expected = new AddOperationHandler();
        Assert.assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void supplyOperationTest() {
        TransactionDto transaction
                = new TransactionDto("s", new Fruit("banana"), 15);
        OperationHandler actual = strategy.getHandler(transaction);
        OperationHandler expected = new AddOperationHandler();
        Assert.assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    public void purchaseOperationTest() {
        Storage.storage.put(new Fruit("banana"), 100);
        TransactionDto transaction
                = new TransactionDto("p", new Fruit("banana"), 50);
        OperationHandler actual = strategy.getHandler(transaction);
        OperationHandler expected = new SubtractOperationHandler();
        Assert.assertEquals(expected.getClass(), actual.getClass());
    }
}
