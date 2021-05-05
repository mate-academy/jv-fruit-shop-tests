package core.basesyntax.service.implementations;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.OperationType;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.operations.OperationDecreaseHandler;
import core.basesyntax.service.operations.OperationHandler;
import core.basesyntax.service.operations.OperationIncreaseHandler;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class OperationStrategyImplTest {
    private static FruitDao fruitDao;
    private static OperationStrategy operationStrategy;
    private static Map<String, OperationHandler> handlers;

    @BeforeClass
    public static void beforeClass() throws Exception {
        fruitDao = new FruitDaoImpl();
        handlers = new HashMap<>();
        handlers.put("b", new OperationIncreaseHandler(fruitDao));
        handlers.put("s", new OperationIncreaseHandler(fruitDao));
        handlers.put("r", new OperationIncreaseHandler(fruitDao));
        handlers.put("p", new OperationDecreaseHandler(fruitDao));
        operationStrategy = new OperationStrategyImpl(handlers);
    }

    @Test
    public void OperationStrategyImplTest_get_balance_Ok() {
        Class<? extends OperationHandler> excepted
                = OperationIncreaseHandler.class;
        Class<? extends OperationHandler> actual
                = operationStrategy.get(OperationType.BALANCE).getClass();
        Assert.assertEquals(excepted, actual);
    }

    @Test
    public void OperationStrategyImplTest_get_supply_Ok() {
        Class<? extends OperationHandler> excepted
                = OperationIncreaseHandler.class;
        Class<? extends OperationHandler> actual
                = operationStrategy.get(OperationType.SUPPLY).getClass();
        Assert.assertEquals(excepted, actual);
    }

    @Test
    public void OperationStrategyImplTest_get_return_Ok() {
        Class<? extends OperationHandler> excepted
                = OperationIncreaseHandler.class;
        Class<? extends OperationHandler> actual
                = operationStrategy.get(OperationType.RETURN).getClass();
        Assert.assertEquals(excepted, actual);
    }
    @Test
    public void OperationStrategyImplTest_get_purchase_Ok() {
        Class<? extends OperationHandler> excepted
                = OperationDecreaseHandler.class;
        Class<? extends OperationHandler> actual
                = operationStrategy.get(OperationType.PURCHASE).getClass();
        Assert.assertEquals(excepted, actual);
    }
}