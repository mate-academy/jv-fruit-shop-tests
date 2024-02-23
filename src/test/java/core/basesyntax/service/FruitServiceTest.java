package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.impl.FruitServiceImpl;
import core.basesyntax.strategy.BalanceHandlerImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.PurchaseHandlerImpl;
import core.basesyntax.strategy.ReturnHandlerImpl;
import core.basesyntax.strategy.SupplyHandlerImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitServiceTest {
    private static FruitService fruitService;

    @BeforeAll
    static void beforeAll() {
        Map<Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(Operation.BALANCE, new BalanceHandlerImpl());
        operationHandlerMap.put(Operation.SUPPLY, new SupplyHandlerImpl());
        operationHandlerMap.put(Operation.PURCHASE, new PurchaseHandlerImpl());
        operationHandlerMap.put(Operation.RETURN, new ReturnHandlerImpl());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        fruitService = new FruitServiceImpl(operationStrategy);
    }

    @Test
    void processTransactions_fruitTransactionsNull_ok() {
        fruitService.processTransactions(null);
        Assertions.assertTrue(Storage.getStorage().isEmpty());
    }

    @Test
    void processTransactions_fruitTransactionsEmpty_ok() {
        fruitService.processTransactions(new ArrayList<>());
        Assertions.assertTrue(Storage.getStorage().isEmpty());
    }

    @Test
    void processTransactions_fruitTransactionsFilled_ok() {
        fruitService.processTransactions(initFruitTransactions());
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 152);
        expected.put("apple", 90);
        Assertions.assertEquals(2, Storage.getStorage().size());
        for (Map.Entry<String, Integer> actual : Storage.getStorage().entrySet()) {
            Assertions.assertTrue(expected.containsKey(actual.getKey()));
            Assertions.assertEquals(expected.get(actual.getKey()), actual.getValue());
        }
    }

    @Test
    void processTransactions_fruitsDifferent_notOk() {
        fruitService.processTransactions(initFruitTransactions());
        Map<String, Integer> expected = new HashMap<>();
        expected.put("mango", 152);
        expected.put("orange", 90);
        Assertions.assertEquals(2, Storage.getStorage().size());
        for (Map.Entry<String, Integer> actual : Storage.getStorage().entrySet()) {
            Assertions.assertFalse(expected.containsKey(actual.getKey()));
            Assertions.assertNotEquals(expected.get(actual.getKey()), actual.getValue());
        }
    }

    @Test
    void processTransactions_quantityDifferent_notOk() {
        fruitService.processTransactions(initFruitTransactions());
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 102);
        expected.put("apple", 80);
        Assertions.assertEquals(2, Storage.getStorage().size());
        for (Map.Entry<String, Integer> actual : Storage.getStorage().entrySet()) {
            Assertions.assertTrue(expected.containsKey(actual.getKey()));
            Assertions.assertNotEquals(expected.get(actual.getKey()), actual.getValue());
        }
    }

    @AfterEach
    void afterEachTest() {
        Storage.getStorage().clear();
    }

    private List<FruitTransaction> initFruitTransactions() {
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        fruitTransactions.add(new FruitTransaction(Operation.getByType("b"), "banana", 20));
        fruitTransactions.add(new FruitTransaction(Operation.getByType("b"), "apple", 100));
        fruitTransactions.add(new FruitTransaction(Operation.getByType("s"), "banana", 100));
        fruitTransactions.add(new FruitTransaction(Operation.getByType("p"), "banana", 13));
        fruitTransactions.add(new FruitTransaction(Operation.getByType("p"), "apple", 20));
        fruitTransactions.add(new FruitTransaction(Operation.getByType("r"), "apple", 10));
        fruitTransactions.add(new FruitTransaction(Operation.getByType("p"), "banana", 5));
        fruitTransactions.add(new FruitTransaction(Operation.getByType("s"), "banana", 50));
        return fruitTransactions;
    }
}
