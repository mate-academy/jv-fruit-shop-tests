package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitInStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.PrecessDataServiceImpl;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.handler.BalanceHandler;
import core.basesyntax.strategy.handler.OperationHandler;
import core.basesyntax.strategy.handler.PurchaseHandler;
import core.basesyntax.strategy.handler.ReturnHandler;
import core.basesyntax.strategy.handler.SupplyHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PrecessDataServiceTest {
    private static OperationStrategy operationStrategy;
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;
    private PrecessDataService precessDataService;

    @BeforeAll
    static void beforeAll() {
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, new ReturnHandler());

        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @BeforeEach
    void setUp() {
        Storage.reset();
        precessDataService = new PrecessDataServiceImpl(operationStrategy);
    }

    @Test
    void writeToStorage_emptyList_Ok() {
        List<FruitTransaction> transactions = new ArrayList<>();
        precessDataService.writeToStorage(transactions);
        assertTrue(Storage.fruits().isEmpty());
    }

    @Test
    void writeToStorage_validData_Ok() {
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20));
        transactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100));
        transactions.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100));
        precessDataService.writeToStorage(transactions);

        assertEquals(2, Storage.fruits().size());

        FruitInStorage banana = new FruitInStorage("banana", 120);
        FruitInStorage apple = new FruitInStorage("apple", 100);
        assertTrue(Storage.fruits().containsValue(banana));
        assertTrue(Storage.fruits().containsValue(apple));
    }
}
