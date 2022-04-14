package core.basesyntax.service.strategy;

import core.basesyntax.dao.ProductDao;
import core.basesyntax.dao.ProductDaoImpl;
import core.basesyntax.model.Operation;
import core.basesyntax.service.operation.OperationDecreaseHandler;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.OperationIncreaseHandler;
import core.basesyntax.service.strategy.impl.OperationStrategyImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyTest {
    private static ProductDao productDao;
    private static OperationStrategy operationStrategy;
    private static Map<String, OperationHandler> handlerMap;

    @BeforeClass
    public static void beforeClass() throws Exception {
        productDao = new ProductDaoImpl();
        handlerMap = new HashMap<>();
        handlerMap.put("b", new OperationIncreaseHandler(productDao));
        handlerMap.put("s", new OperationIncreaseHandler(productDao));
        handlerMap.put("r", new OperationIncreaseHandler(productDao));
        handlerMap.put("p", new OperationDecreaseHandler(productDao));
        operationStrategy = new OperationStrategyImpl(handlerMap);
    }

    @Test
    public void getOperationBalanceHandler_isOk() {
        Class<? extends OperationHandler> excepted =
                OperationIncreaseHandler.class;
        Class<? extends OperationHandler> actual
                = operationStrategy.get(Operation.BALANCE).getClass();
        Assert.assertEquals(excepted, actual);
    }

    @Test
    public void getOperationSupplyHandler_isOk() {
        Class<? extends OperationHandler> excepted =
                OperationIncreaseHandler.class;
        Class<? extends OperationHandler> actual =
                operationStrategy.get(Operation.SUPPLY).getClass();
        Assert.assertEquals(excepted, actual);
    }

    @Test
    public void getOperationReturnHandler_isOk() {
        Class<? extends OperationHandler> excepted =
                OperationIncreaseHandler.class;
        Class<? extends OperationHandler> actual =
                operationStrategy.get(Operation.RETURN).getClass();
        Assert.assertEquals(excepted, actual);
    }

    @Test
    public void getOperationPurchaseHandler_isOk() {
        Class<? extends OperationHandler> excepted =
                OperationDecreaseHandler.class;
        Class<? extends OperationHandler> actual =
                operationStrategy.get(Operation.PURCHASE).getClass();
        Assert.assertEquals(excepted, actual);
    }
}
