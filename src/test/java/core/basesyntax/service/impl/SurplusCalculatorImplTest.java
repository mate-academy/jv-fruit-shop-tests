package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.SurplusCalculator;
import core.basesyntax.storage.TemporaryStorage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SurplusCalculatorImplTest {
    private static SurplusCalculator surplusCalculator;
    private static List<FruitTransaction> testList;
    private static Map<String, Integer> testMap;

    @BeforeAll
    static void beforeAll() {
        surplusCalculator = new SurplusCalculatorImpl();
        testList = new ArrayList<>();
        testMap = new HashMap<>();
    }

    @AfterEach
    void tearDown() {
        TemporaryStorage.temporaryStorage.clear();
        testMap.clear();
    }

    @Test
    void calculateData_properListTwoOperation_ok() {
        testList = List.of(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 450),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 80));
        surplusCalculator.calculateData(testList);
        testMap.put("banana", 370);
        Map<String, Integer> expected = testMap;
        Map<String, Integer> actual = TemporaryStorage.temporaryStorage;

        assertEquals(expected, actual);
    }

    @Test
    void calculateData_properListFiveOperation_ok() {
        testList = List.of(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 450),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 80),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "grape", 460),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 120),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 80));
        surplusCalculator.calculateData(testList);
        testMap.put("banana", 570);
        testMap.put("grape", 460);
        Map<String, Integer> expected = testMap;
        Map<String, Integer> actual = TemporaryStorage.temporaryStorage;

        assertEquals(expected, actual);
    }

    @Test
    void calculateData_properListNineOperation_ok() {
        testList = List.of(new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 654),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 842),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "grape", 1104),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "grape", 410),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 640),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 200),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "grape", 342),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 478),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "grape", 200));
        surplusCalculator.calculateData(testList);
        testMap.put("apple", 1094);
        testMap.put("banana", 364);
        testMap.put("grape", 1372);
        Map<String, Integer> expected = testMap;
        Map<String, Integer> actual = TemporaryStorage.temporaryStorage;

        assertEquals(expected, actual);
    }

    @Test
    void calculateData_emptyList_notOk() {
        testList = Collections.emptyList();
        assertThrows(RuntimeException.class, () -> surplusCalculator.calculateData(testList),
                "\"RuntimeException\" should be thrown when the input list is empty");
    }

    @Test
    void calculateData_nullList_notOk() {
        testList = null;
        assertThrows(RuntimeException.class, () -> surplusCalculator.calculateData(testList),
                "\"RuntimeException\" should be thrown when the input list is null");
    }
}
