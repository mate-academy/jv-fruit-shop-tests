package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.strategy.AddOperationHandler;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseOperationHandler;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static Map<String, OperationHandler> operationHandlerMap = new HashMap<>();
    private static OperationStrategy operationStrategy;
    private static FruitDao fruitDao;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        operationHandlerMap.put("s", new AddOperationHandler(fruitDao));
        operationHandlerMap.put("b", new BalanceOperationHandler(fruitDao));
        operationHandlerMap.put("p", new PurchaseOperationHandler(fruitDao));
        operationHandlerMap.put("r", new AddOperationHandler(fruitDao));
    }

    @Test
    public void getOperationHandler_AddOperationHandler_ok() {
        String operation = "s";
        AddOperationHandler actual = (AddOperationHandler)
                operationStrategy.getOperationHandler(operation);
        AddOperationHandler expexted = new AddOperationHandler(fruitDao);
        Assert.assertEquals(expexted.getClass(), actual.getClass());
    }

    @Test
    public void getOperationHandler_BalanceOperationHandler_ok() {
        String operation = "b";
        BalanceOperationHandler actual = (BalanceOperationHandler)
                operationStrategy.getOperationHandler(operation);
        BalanceOperationHandler expexted = new BalanceOperationHandler(fruitDao);
        Assert.assertEquals(expexted.getClass(), actual.getClass());
    }

    @Test
    public void getOperationHandler_PurchaseOperationHandler_ok() {
        String operation = "p";
        PurchaseOperationHandler actual = (PurchaseOperationHandler)
                operationStrategy.getOperationHandler(operation);
        PurchaseOperationHandler expexted = new PurchaseOperationHandler(fruitDao);
        Assert.assertEquals(expexted.getClass(), actual.getClass());
    }

    @Test
    public void getOperationHandler_AddOperationHandler2_ok() {
        String operation = "r";
        AddOperationHandler actual = (AddOperationHandler)
                operationStrategy.getOperationHandler(operation);
        AddOperationHandler expexted = new AddOperationHandler(fruitDao);
        Assert.assertEquals(expexted.getClass(), actual.getClass());
    }

    @Test
    public void getOperationHandler_wrongOperation_notOk() throws Exception {
    }

    @Test
    public void getOperationHandler_wrongOperationL_notOk() throws Exception {
        PurchaseOperationHandler expected = new PurchaseOperationHandler(fruitDao);
        Map<String, OperationHandler> operationHandlerMap
                = expected.getOperationHandlerMap(Locale.forLanguageTag("a"));
    }
}
