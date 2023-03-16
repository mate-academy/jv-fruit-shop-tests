package service;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.FruitTransaction;
import org.junit.BeforeClass;
import org.junit.Test;
import service.impl.FruitShopServiceImpl;
import strategy.OperationHandler;
import strategy.impl.BalanceOperationImpl;
import strategy.impl.OperationStrategyServiceImpl;

public class FruitShopServiceTest {
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static FruitShopService defaultFruitShopService;
    private static List<FruitTransaction> defaultParsed;
    private static Map<String, Integer> defaultExpected;

    @BeforeClass
    public static void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationImpl());
        defaultFruitShopService =
                new FruitShopServiceImpl(new OperationStrategyServiceImpl(operationHandlerMap));
        defaultParsed = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, BANANA, 50));
        defaultExpected = new HashMap<>();
        defaultExpected.put(BANANA, 50);
    }

    @Test
    public void report_defaultCase_ok() {
        assertEquals(defaultExpected, defaultFruitShopService.processData(defaultParsed));
    }

    @Test
    public void report_emptyInput_ok() {
        assertEquals(new HashMap<>(), defaultFruitShopService.processData(List.of()));
    }

    @Test(expected = RuntimeException.class)
    public void constructor_noOperationStrategy_notOk() {
        new FruitShopServiceImpl(null);
    }

    @Test(expected = RuntimeException.class)
    public void report_parsedNull_notOk() {
        defaultFruitShopService.processData(null);
    }

}
