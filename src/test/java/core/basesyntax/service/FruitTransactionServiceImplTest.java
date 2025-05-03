package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.BalanceOperationActivities;
import core.basesyntax.strategy.OperationActivities;
import core.basesyntax.strategy.PurchaseOperationActivities;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitTransactionServiceImplTest {
    private static final Map<FruitTransaction.Operation, OperationActivities>
            operationStrategyMap = new HashMap<>();
    private OperationStrategy operationStrategy;
    private FruitTransactionService fruitTransactionService;

    @BeforeAll
    static void beforeAll() {
        operationStrategyMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationActivities());
        operationStrategyMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationActivities());
    }

    @AfterEach
    void afterEachTest() {
        Storage.fruitTransactions.clear();
    }

    @Test
    void calcFruitTransaction_readData_OK() {
        Map<String, List<FruitTransaction>> expected = createFruits();
        operationStrategy = new OperationStrategyImpl(operationStrategyMap);
        fruitTransactionService = new FruitTransactionServiceImpl(operationStrategy);
        Map<String, List<FruitTransaction>> result =
                fruitTransactionService.readFruitTransaction();
        boolean isEqualsMap = areEqualsMapFruits(expected, result);
        Assert.assertTrue("Error generating map from storage!", isEqualsMap);
    }

    @Test
    void calcFruitTransaction_calcData_OK() {
        Map<String, Integer> expected = createMapResult();
        operationStrategy = new OperationStrategyImpl(operationStrategyMap);
        fruitTransactionService = new FruitTransactionServiceImpl(operationStrategy);
        Map<String, List<FruitTransaction>> transactionMap = createFruits();
        Map<String, Integer> result =
                fruitTransactionService.calcFruitTransaction(transactionMap);
        boolean isEqualsMap = areEqualsMapFruits(expected, result);
        Assert.assertTrue("Transaction map calculation error!", isEqualsMap);
    }

    @Test
    void calcFruitTransaction_calcData_NotOK() {
        operationStrategy = new OperationStrategyImpl(operationStrategyMap);
        fruitTransactionService = new FruitTransactionServiceImpl(operationStrategy);
        Map<String, List<FruitTransaction>> transactionMap = createFruits_NotOK();
        Throwable thrown = Assert.assertThrows(RuntimeException.class, () -> {
            fruitTransactionService.calcFruitTransaction(transactionMap);
        });
        Assert.assertNotNull(thrown.getMessage());
    }

    private Map<String, List<FruitTransaction>> createFruits() {
        FruitTransaction fruitTransaction1 = new FruitTransaction();
        fruitTransaction1.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction1.setFruit("apple");
        fruitTransaction1.setQuantity(100);
        List<FruitTransaction> fruitsList = new ArrayList<>();
        fruitsList.add(fruitTransaction1);
        Storage.fruitTransactions.add(fruitTransaction1);
        FruitTransaction fruitTransaction2 = new FruitTransaction();
        fruitTransaction2.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction2.setFruit("apple");
        fruitTransaction2.setQuantity(20);
        fruitsList.add(fruitTransaction2);
        Storage.fruitTransactions.add(fruitTransaction2);
        Map<String, List<FruitTransaction>> fruitsMap = new HashMap<>();
        fruitsMap.put("apple", fruitsList);
        return fruitsMap;
    }

    private Map<String, List<FruitTransaction>> createFruits_NotOK() {
        FruitTransaction fruitTransaction1 = new FruitTransaction();
        fruitTransaction1.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction1.setFruit("apple");
        fruitTransaction1.setQuantity(100);
        List<FruitTransaction> fruitsList = new ArrayList<>();
        fruitsList.add(fruitTransaction1);
        Storage.fruitTransactions.add(fruitTransaction1);
        FruitTransaction fruitTransaction2 = new FruitTransaction();
        fruitTransaction2.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction2.setFruit("apple");
        fruitTransaction2.setQuantity(200);
        fruitsList.add(fruitTransaction2);
        Storage.fruitTransactions.add(fruitTransaction2);
        Map<String, List<FruitTransaction>> fruitsMap = new HashMap<>();
        fruitsMap.put("apple", fruitsList);
        return fruitsMap;
    }

    private Map<String, Integer> createMapResult() {
        Map<String, Integer> result = new HashMap<>();
        result.put("apple", Integer.valueOf(80));
        return result;
    }

    private boolean areEqualsMapFruits(Map<?, ?> first, Map<?, ?> second) {
        if (first.size() != second.size()) {
            return false;
        }
        return first.entrySet().stream()
                .allMatch(e -> e.getValue().equals(second.get(e.getKey())));
    }
}
