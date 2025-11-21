package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.impl.ShopServiceImpl;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.PurchaseOperationHandler;
import core.basesyntax.strategy.ReturnOperationHandler;
import core.basesyntax.strategy.SupplyOperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private static FruitDao fruitDao;
    private static ShopService shopService;
    private static Map<FruitTransaction.Type,
            OperationHandler> operationHandlerMap = new HashMap<>();

    @BeforeAll
    static void beforeAll() {
        fruitDao = new FruitDaoImpl();

        BalanceOperationHandler balanceOperationHandler = new BalanceOperationHandler(fruitDao);
        SupplyOperationHandler supplyOperationHandler = new SupplyOperationHandler(fruitDao);
        ReturnOperationHandler returnOperationHandler = new ReturnOperationHandler(fruitDao);
        PurchaseOperationHandler purchaseOperationHandler = new PurchaseOperationHandler(fruitDao);

        operationHandlerMap.put(FruitTransaction.Type.BALANCE,
                balanceOperationHandler);
        operationHandlerMap.put(FruitTransaction.Type.SUPPLY,
                supplyOperationHandler);
        operationHandlerMap.put(FruitTransaction.Type.RETURN,
                returnOperationHandler);
        operationHandlerMap.put(FruitTransaction.Type.PURCHASE,
                purchaseOperationHandler);

        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);

        shopService = new ShopServiceImpl(operationStrategy);
    }

    @BeforeEach
    void setUp() {
        FruitStorage.clearStorage();
    }

    @Test
    void process_correctData_saveToStorage() {
        Map<String, Integer> storageData = Map.of("banana", 25, "apple", 98);

        List<String> correctFruitData = List.of("b,banana,20", "s,apple,100",
                "r,banana,5", "p,apple,2");

        shopService.process(correctFruitData);
        assertEquals(storageData, fruitDao.getAll());
    }

    @Test
    void process_invalidData_notOk() {
        List<String> invalidFruitData = List.of("x,apple,30", "t,banana,20");
        assertThrows(RuntimeException.class, () -> shopService.process(invalidFruitData));
    }
}
