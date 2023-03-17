package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.FruitShopServiceImpl;
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
import org.junit.Before;
import org.junit.Test;

public class FruitShopServiceTest {
    private static List<FruitTransaction> defaultParsed;
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;
    private static Map<String, Integer> expectedMap;
    private static FruitShopService fruitShopService;

    @Before
    public void beforeAll() {
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        fruitShopService = new FruitShopServiceImpl(new OperationStrategyImpl(operationHandlerMap));
        defaultParsed = List.of(new FruitTransaction("b","banana",100),
                new FruitTransaction("s","banana",30),
                new FruitTransaction("p","banana",50),
                new FruitTransaction("r","banana",20),
                new FruitTransaction("b","apple",15));
        expectedMap = new HashMap<>();
        expectedMap.put("banana",100);
        expectedMap.put("apple",15);
    }

    @Test
    public void parse_defaultInput_ok() {
        assertEquals(expectedMap, fruitShopService.finalReport(defaultParsed));
    }

    @Test
    public void parse_emplyInput_ok() {
        assertEquals(new HashMap<>(), fruitShopService.finalReport(new ArrayList<>()));
    }

    @Test(expected = RuntimeException.class)
    public void parse_nullInput_notOk() {
        fruitShopService.finalReport(null);
    }

    @Test(expected = RuntimeException.class)
    public void constructorIsNull_notOk() {
        fruitShopService = new FruitShopServiceImpl(null);
    }
}
