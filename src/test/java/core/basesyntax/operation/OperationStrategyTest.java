package core.basesyntax.operation;

import core.basesyntax.model.Record;
import core.basesyntax.report.FruitBalance;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyTest {
    private static final String BALANCE = "b";
    private static final String SUPPLY = "s";
    private static final String PURCHASE = "p";
    private static final String RETURN = "r";
    private static final String EQUAL_MAP_KEY = "apple";
    private static final int AMOUNT_ADDITION = 20;
    private static final int AMOUNT_DECREASE = 5;
    private static Map<String, OperationHandler> operationHandlerMap;
    private static OperationStrategy operationStrategy;

    @BeforeClass
    public static void beforeAll() {
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(BALANCE, new AdditionHandler());
        operationHandlerMap.put(SUPPLY, new AdditionHandler());
        operationHandlerMap.put(PURCHASE, new DecreaseHandler());
        operationHandlerMap.put(RETURN, new AdditionHandler());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    public void get_balanceOperation_Ok() {
        operationHandlerMap.get(BALANCE).apply(new Record(BALANCE, "apple", 10));
        operationStrategy.get(BALANCE).apply(new Record(BALANCE, "apple", 10));
        int actual = FruitBalance.FRUIT_BALANCE.get(EQUAL_MAP_KEY);
        int expected = AMOUNT_ADDITION;
        Assert.assertEquals("Amounts should add!", expected, actual);
    }

    @Test
    public void get_supplyOperation_Ok() {
        operationHandlerMap.get(SUPPLY).apply(new Record(SUPPLY, "apple", 10));
        operationStrategy.get(SUPPLY).apply(new Record(SUPPLY, "apple", 10));
        int actual = FruitBalance.FRUIT_BALANCE.get(EQUAL_MAP_KEY);
        int expected = AMOUNT_ADDITION;
        Assert.assertEquals("Amounts should add!", expected, actual);
    }

    @Test
    public void get_returnOperation_Ok() {
        operationHandlerMap.get(RETURN).apply(new Record(RETURN, "apple", 10));
        operationStrategy.get(RETURN).apply(new Record(RETURN, "apple", 10));
        int actual = FruitBalance.FRUIT_BALANCE.get(EQUAL_MAP_KEY);
        int expected = AMOUNT_ADDITION;
        Assert.assertEquals("Amounts should add!", expected, actual);
    }

    @Test
    public void get_purchaseOperation_Ok() {
        FruitBalance.FRUIT_BALANCE.put("apple", 20);
        operationHandlerMap.get(PURCHASE).apply(new Record(PURCHASE, "apple", 10));
        operationStrategy.get(PURCHASE).apply(new Record(PURCHASE, "apple", 5));
        int actual = FruitBalance.FRUIT_BALANCE.get(EQUAL_MAP_KEY);
        int expected = AMOUNT_DECREASE;
        Assert.assertEquals("Amounts should subtract!",expected, actual);
    }

    @After
    public void tearDown() {

        FruitBalance.FRUIT_BALANCE.clear();
    }
}
