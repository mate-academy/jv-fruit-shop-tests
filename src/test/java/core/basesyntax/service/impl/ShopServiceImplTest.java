package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitRepository;
import core.basesyntax.dao.impl.FruitRepositoryImpl;
import core.basesyntax.db.Database;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.enums.Operation;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.handler.OperationHandler;
import core.basesyntax.service.handler.OperationStrategy;
import core.basesyntax.service.handler.impl.BalanceOperationHandler;
import core.basesyntax.service.handler.impl.OperationStrategyImpl;
import core.basesyntax.service.handler.impl.PurchaseOperationHandler;
import core.basesyntax.service.handler.impl.ReturnOperationHandler;
import core.basesyntax.service.handler.impl.SupplyOperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static ShopService shopService;
    private static final Map<String, Integer> db = Database.storage;

    @BeforeAll
    static void beforeAll() {
        FruitRepository fruitRepository = new FruitRepositoryImpl();
        Map<Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(Operation.BALANCE, new BalanceOperationHandler(fruitRepository));
        operationHandlerMap.put(Operation.SUPPLY, new SupplyOperationHandler(fruitRepository));
        operationHandlerMap.put(Operation.PURCHASE, new PurchaseOperationHandler(fruitRepository));
        operationHandlerMap.put(Operation.RETURN, new ReturnOperationHandler(fruitRepository));
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @AfterEach
    void tearDown() {
        db.clear();
    }

    @Test
    void process_ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(Operation.BALANCE, BANANA, 20),
                new FruitTransaction(Operation.BALANCE, APPLE, 20),
                new FruitTransaction(Operation.PURCHASE, BANANA, 10)
        );
        shopService.process(transactions);
        Map<String, Integer> expected = Map.of(
                BANANA, 10,
                APPLE, 20
        );
        assertEquals(expected, db);
    }
}
