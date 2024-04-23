package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.service.TransactionHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitShopServiceImplTest {
    private FruitShopService fruitShopService;

    @BeforeEach
    public void setUp() {
        Map<String, TransactionHandler> operationMap = new HashMap<>();
        operationMap.put("b", new BalanceTransactionHandler());
        operationMap.put("r", new ReturnTransactionHandler());
        OperationStrategy strategy = new OperationStrategy(operationMap);
        fruitShopService = new FruitShopServiceImpl(strategy);
        Storage.storage.put("apple", 10);
    }

    @Test
    public void processTransaction_BalanceTransaction_ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.BALANCE, "apple", 10);
        List<FruitTransaction> transactions = List.of(transaction);
        fruitShopService.processTransaction(transactions);
        Map<String, Integer> expected = Map.of("apple", 10);
        Map<String, Integer> actual = Storage.storage;
        assertEquals(expected, actual);
    }

    @Test
    public void processTransaction_ReturnTransaction() {
        FruitTransaction transaction = new FruitTransaction(Operation.RETURN, "apple", 5);
        List<FruitTransaction> transactions = List.of(transaction);
        fruitShopService.processTransaction(transactions);
        Map<String, Integer> expected = Map.of("apple", 15);
        Map<String, Integer> actual = Storage.storage;
        assertEquals(expected, actual);
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }
}
