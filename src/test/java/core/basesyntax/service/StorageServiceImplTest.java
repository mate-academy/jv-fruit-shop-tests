package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.BalanceTransactionHandler;
import core.basesyntax.service.impl.PurchaseTransactionHandler;
import core.basesyntax.service.impl.ReturnTransactionHandler;
import core.basesyntax.service.impl.SupplyTransactionHandler;
import core.basesyntax.service.impl.TransactionHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class StorageServiceImplTest {
    private static StorageService storageService;
    private static Map<FruitTransaction.Operation, TransactionHandler> transactionHandlersMap;
    private static OperationStrategy operationStrategy;
    private static FruitService fruitService;

    @BeforeAll
    static void beforeAll() {
        transactionHandlersMap = new HashMap<>();
        transactionHandlersMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceTransactionHandler());
        transactionHandlersMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseTransactionHandler());
        transactionHandlersMap.put(FruitTransaction.Operation.RETURN,
                new ReturnTransactionHandler());
        transactionHandlersMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyTransactionHandler());
        operationStrategy = new OperationStrategyImpl(transactionHandlersMap);
        fruitService = new FruitServiceImpl();
        storageService = new StorageServiceImpl(operationStrategy, fruitService);
    }

    @Test
    void fruitTransactionList_WithEmptyList() {
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        storageService.processTransactions(fruitTransactions);
        assertEquals(Storage.remnantsOfGoods.size(), 0);

    }

    @Test
    void processDifferentTypesOfTransactions() {
        List<FruitTransaction> fruitTransactions = List.of(
                new FruitTransaction("b", "apple", "20"),
                new FruitTransaction("s", "banana", "15"),
                new FruitTransaction("p", "apple", "5"),
                new FruitTransaction("r", "banana", "5")
        );
        storageService.processTransactions(fruitTransactions);
        int appleQuantity = Storage.remnantsOfGoods.getOrDefault("apple", 0);
        int bananaQuantity = Storage.remnantsOfGoods.getOrDefault("banana", 0);
        assertEquals(15, appleQuantity);
        assertEquals(20, bananaQuantity);
    }

    @AfterEach
    public void afterEachTest() {
        Storage.remnantsOfGoods.clear();
    }
}
