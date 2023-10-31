package core.basesyntax.service.impl;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.StrategyProcessor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class StorageStrategyProcessorTest {
    private static Storage storage;
    private static List<FruitTransaction> fruitList;
    private static StrategyProcessor strategyProcessor;

    @BeforeAll
    public static void setUp() {
        storage = new FruitStorage();
        fruitList = new ArrayList<>();
        strategyProcessor = new StorageStrategyProcessor(storage);
        fruitList.add(new FruitTransaction(
                FruitTransaction.Operation.getByCode("b"), "banana", 20));
        fruitList.add(new FruitTransaction(
                FruitTransaction.Operation.getByCode("b"), "apple", 100));
        fruitList.add(new FruitTransaction(
                FruitTransaction.Operation.getByCode("s"), "banana", 100));
        fruitList.add(new FruitTransaction(
                FruitTransaction.Operation.getByCode("p"), "banana", 13));
        fruitList.add(new FruitTransaction(
                FruitTransaction.Operation.getByCode("r"), "apple", 10));
        fruitList.add(new FruitTransaction(
                FruitTransaction.Operation.getByCode("p"), "apple", 20));
        fruitList.add(new FruitTransaction(
                FruitTransaction.Operation.getByCode("p"), "banana", 5));
        fruitList.add(new FruitTransaction(
                FruitTransaction.Operation.getByCode("s"), "banana", 50));
    }

    @Test
    public void processFruitTransaction_validData_Ok() {
        strategyProcessor.processTransactionStrategies(fruitList);
        HashMap<String, Integer> expectedStorage = new HashMap<>();
        expectedStorage.put("banana", 152);
        expectedStorage.put("apple", 90);
        Set<String> expectedKeys = expectedStorage.keySet();
        Set<Integer> expectedValues = new HashSet<>();

        for (Map.Entry<String,Integer> set : expectedStorage.entrySet()) {
            expectedValues.add(set.getValue());
        }

        Set<String> actualKeys = storage.getAllItems();
        Set<Integer> actualValues = new HashSet<>();
        for (String k : actualKeys) {
            actualValues.add(storage.getQuantity(k));
        }

        Assert.assertEquals(actualKeys,expectedKeys);
        Assert.assertEquals(expectedValues, actualValues);
    }
}
