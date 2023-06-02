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

class PurchaseOperationServiceTest {
    private static Map<String, Integer> testStorage;
    private static FruitTransaction fruitTransaction;
    private static OperationService operationService;

    @BeforeAll
    static void beforeAll() {
        operationService = new PurchaseOperationService();
        testStorage = new HashMap<>();
    }

    @AfterEach
    void tearDown() {
        testStorage.clear();
        TemporaryStorage.temporaryStorage.clear();
    }

    @Test
    void performOperation_doOnePurchaseOperation_ok() {
        TemporaryStorage.temporaryStorage.put("apple", 500);

        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,"apple",40);
        operationService.performOperation(fruitTransaction);

        testStorage.put("apple", 460);
        Map<String, Integer> expected = testStorage;
        Map<String, Integer> actual = TemporaryStorage.temporaryStorage;

        assertEquals(expected, actual);
    }

    @Test
    void performOperation_doThreePurchaseOperation_ok() {
        TemporaryStorage.temporaryStorage.put("apple", 500);

        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,"apple",40);
        operationService.performOperation(fruitTransaction);
        operationService.performOperation(fruitTransaction);
        operationService.performOperation(fruitTransaction);

        testStorage.put("apple", 380);
        Map<String, Integer> expected = testStorage;
        Map<String, Integer> actual = TemporaryStorage.temporaryStorage;

        assertEquals(expected, actual);
    }

    @Test
    void performOperation_doOnePurchaseOperation_notOk() {
        TemporaryStorage.temporaryStorage.put("grape", 100);
        fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,"grape", 120);
        assertThrows(RuntimeException.class,
                () -> operationService.performOperation(fruitTransaction),
                "\"RuntimeException\" should be thrown when there "
                        + "is not enough fruit on the balance sheet for purchase");

    }
}

