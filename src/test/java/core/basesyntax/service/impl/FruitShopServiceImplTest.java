package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitShopServiceImplTest {

    private static FruitShopService defaultFruitShopService;
    private static List<FruitTransaction> defaultParsed;
    private static Map<String, Integer> defaultExpected;

    @BeforeClass
    public static void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        defaultFruitShopService =
                new FruitShopServiceImpl(new OperationStrategyImpl(operationHandlerMap));
        defaultParsed = List.of(
                new FruitTransaction("b", "banana", 100),
                new FruitTransaction("b", "apple", 1),
                new FruitTransaction("r", "banana", 10),
                new FruitTransaction("p", "banana", 10),
                new FruitTransaction("s", "banana", 10)
        );
        defaultExpected = new HashMap<>();
        defaultExpected.put("banana", 110);
        defaultExpected.put("apple", 1);
    }

    @Test
    public void report_defaultCase_ok() {
        assertEquals(defaultFruitShopService.report(defaultParsed), defaultExpected);
    }

    @Test
    public void report_emptyInput_ok() {
        assertEquals(defaultFruitShopService.report(new ArrayList<>()), new HashMap<>());
    }

    @Test(expected = RuntimeException.class)
    public void report_noOperationStrategy_notOk() {
        new FruitShopServiceImpl(null);
    }

    @Test(expected = RuntimeException.class)
    public void report_parsedNull_notOk() {
        defaultFruitShopService.report(null);
    }
}
