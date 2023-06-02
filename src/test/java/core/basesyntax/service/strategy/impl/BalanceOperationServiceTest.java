package core.basesyntax.service.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.strategy.OperationService;
import core.basesyntax.storage.TemporaryStorage;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceOperationServiceTest {
    private static Map<String, Integer> testStorage;
    private static FruitTransaction fruitTransaction;
    private static OperationService operationService;

    @BeforeAll
    static void beforeAll() {
        operationService = new BalanceOperationService();
        testStorage = new HashMap<>();
    }

    @AfterEach
    void tearDown() {
        testStorage.clear();
        TemporaryStorage.temporaryStorage.clear();
    }

    @Test
    void performOperation_doOneBalanceOperation_ok() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,"apple",500);
        operationService.performOperation(fruitTransaction);

        testStorage.put("apple", 500);
        Map<String, Integer> expected = testStorage;
        Map<String, Integer> actual = TemporaryStorage.temporaryStorage;

        assertEquals(expected, actual);
    }

    @Test
    void performOperation_doThreeBalanceOperation_ok() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,"apple",500);
        operationService.performOperation(fruitTransaction);
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,"peach", 555);
        operationService.performOperation(fruitTransaction);
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,"orange", 144);
        operationService.performOperation(fruitTransaction);

        testStorage.put("apple", 500);
        testStorage.put("peach", 555);
        testStorage.put("orange", 144);
        Map<String, Integer> expected = testStorage;
        Map<String, Integer> actual = TemporaryStorage.temporaryStorage;

        assertEquals(expected, actual);
    }

    @Test
    void performOperation_fruitAlreadyOnBalance_notOk() {
        TemporaryStorage.temporaryStorage.put("avocado", 455);
        fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,"avocado",1007);
        assertThrows(RuntimeException.class,
                () -> operationService.performOperation(fruitTransaction),
                "\"RuntimeException\" should be thrown when fruit already on balance sheet");
    }
}
