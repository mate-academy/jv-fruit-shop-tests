package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.OperationProcessorImpl;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.handler.BalanceHandler;
import core.basesyntax.strategy.handler.OperationHandler;
import core.basesyntax.strategy.handler.PurchaseHandler;
import core.basesyntax.strategy.handler.ReturnHandler;
import core.basesyntax.strategy.handler.SupplyHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PrecessDataServiceTest {
    private static OperationStrategy operationStrategy;
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;
    private static OperationProcessor precessDataService;

    @BeforeAll
    static void beforeAll() {
        operationHandlerMap = Map.of(FruitTransaction.Operation.BALANCE, new BalanceHandler(),
                FruitTransaction.Operation.SUPPLY, new SupplyHandler(),
                FruitTransaction.Operation.PURCHASE, new PurchaseHandler(),
                FruitTransaction.Operation.RETURN, new ReturnHandler());

        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        precessDataService = new OperationProcessorImpl(operationStrategy);
    }

    @AfterEach
    void tearDown() {
        Storage.FRUITS.clear();
    }

    @Test
    void writeToStorage_emptyList_Ok() {
        List<FruitTransaction> transactions = new ArrayList<>();
        precessDataService.applyOperation(transactions);
        assertTrue(Storage.FRUITS.isEmpty());
    }

    @Test
    void writeToStorage_validData_Ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100));
        precessDataService.applyOperation(transactions);

        assertEquals(2, Storage.FRUITS.size());
        assertEquals(120, Storage.FRUITS.get("banana"));
        assertEquals(100, Storage.FRUITS.get("apple"));
    }
}
