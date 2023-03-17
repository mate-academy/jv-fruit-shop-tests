package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitShopServiceTest {
    private static List<FruitTransaction> defaultParsed;
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;
    private static Map<String, Integer> expectedMap;
    private static FruitShopService fruitShopService;

    @BeforeAll
    static void beforeAll() {
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
    void parse_defaultInput_ok() {
        assertEquals(fruitShopService.finalReport(defaultParsed), expectedMap);
    }

    @Test
    void parse_emplyInput_ok() {
        assertEquals(fruitShopService.finalReport(new ArrayList<>()), new HashMap<>());
    }

    @Test
    void parse_nullInput_notOk() {
        assertThrows(RuntimeException.class, () -> {
            fruitShopService.finalReport(null);
        });
    }

    @Test
    void constructorIsNull_notOk() {
        assertThrows(RuntimeException.class, () -> {
            new FruitShopServiceImpl(null);
        });
    }
}
