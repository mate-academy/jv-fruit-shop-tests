package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
    private static FruitShopService fruitShopService;
    private static List<FruitTransaction> parsed;
    private static Map<String, Integer> expected;

    @BeforeClass
    public static void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        fruitShopService = new FruitShopServiceImpl(new OperationStrategyImpl(operationHandlerMap));
        parsed = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "orange", 45),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana",20),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "orange", 75),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "orange", 10),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "orange", 20)
        );
        expected = new HashMap<>();
        expected.put("orange", 110);
        expected.put("banana", 20);
    }

    @Test
    public void process_defaultCase_ok() {
        assertEquals(expected, fruitShopService.process(parsed));
    }

    @Test
    public void process_emptyInput_ok() {
        assertTrue(fruitShopService.process(new ArrayList<>()).isEmpty());
    }
}
