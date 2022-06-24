package core.basesyntax.strategy.impl;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.FruitsDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.FruitHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static FruitsDao fruitsDao;
    private static FruitTransaction fruitTransaction;
    private static OperationStrategy operationStrategy;
    private static Map<FruitTransaction.Operation, FruitHandler> fruitHandlerMap;

    @BeforeClass
    public static void beforeClass() {
        fruitsDao = new FruitsDaoImpl();
        fruitHandlerMap = new HashMap<>();
        fruitHandlerMap.put(
                FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler(fruitsDao));
        operationStrategy = new OperationStrategyImpl(fruitHandlerMap);
        fruitsDao.add("mango", 20);
    }

    @Test(expected = RuntimeException.class)
    public void handle_nullValueOfOperation_notOk() {
        operationStrategy.get(fruitTransaction.getOperation()).handleOperation(null);
    }

    @Test(expected = RuntimeException.class)
    public void handle_wrongOperationValue_notOk() {
        fruitTransaction = new FruitTransaction("c", "banana", 20);
        operationStrategy.get(fruitTransaction.getOperation()).handleOperation(fruitTransaction);
    }

    @Test
    public void handle_validValue_isOk() {
        fruitTransaction = new FruitTransaction("s", "mango", 15);
        operationStrategy.get(fruitTransaction.getOperation()).handleOperation(fruitTransaction);
        int actual = fruitsDao.get("mango");
        int expected = 35;
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void handle_nullOperationValue_notOk() {
        fruitTransaction = new FruitTransaction(null, "banana", 20);
        operationStrategy.get(fruitTransaction.getOperation()).handleOperation(fruitTransaction);
    }
}
