package core.basesyntax.service.operation.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.OperationStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static FruitDao fruitDao;
    private static OperationStrategy operationStrategy;
    private static String[] lineInfo = new String[]{"b", "apple", "30"};
    private static Map<String, OperationHandler> operationHandlesMap = new HashMap<>();
    private Class clazz;

    @BeforeClass
    public static void setUp() {
        operationHandlesMap.put("s",new SupplyOperationHandler(fruitDao));
        operationHandlesMap.put("p",new PurchaseOperationHandler(fruitDao));
        operationHandlesMap.put("r",new ReturnOperationHandler(fruitDao));
        operationHandlesMap.put("b",new BalanceOperationHandler(fruitDao));
        fruitDao = new FruitDaoImpl();
        operationStrategy = new OperationStrategyImpl(fruitDao);
    }

    @Test
    public void getHandlerByValidKey_Ok() {
        operationStrategy.get(lineInfo);
        Assert.assertTrue(Storage.getFruits().contains(new Fruit.FruitBuilder()
                .setName(lineInfo[1])
                .build()));
        Assert.assertEquals(30,fruitDao.get(lineInfo[1]).getQuantity());
    }

    @Test
    public void getHandlerByInvalidKey_NotOk() {
        lineInfo[0] = "c";
        try {
            operationStrategy.get(lineInfo);
        } catch (RuntimeException e) {
            clazz = RuntimeException.class;
        }
        Assert.assertEquals("RuntimeException must be thrown",
                RuntimeException.class,clazz = RuntimeException.class);
    }

    @After
    public void after() {
        lineInfo = new String[]{"b", "apple", "30"};
        Storage.getFruits().clear();
    }
}
