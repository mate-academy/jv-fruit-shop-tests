package core.basesyntax.service.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.strategy.OperationService;
import core.basesyntax.storage.TemporaryStorage;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupplyOperationServiceTest {
    private static Map<String, Integer> testStorage;
    private static FruitTransaction fruitTransaction;
    private static OperationService operationService;

    @BeforeAll
    static void beforeAll() {
        operationService = new SupplyOperationService();
        testStorage = new HashMap<>();
    }

    @AfterEach
    void tearDown() {
        testStorage.clear();
        TemporaryStorage.temporaryStorage.clear();
    }

    @Test
    void performOperation_doOneSupplyOperation_ok() {
        TemporaryStorage.temporaryStorage.put("lemon", 14800);

        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,"lemon",4118);
        operationService.performOperation(fruitTransaction);

        testStorage.put("lemon", 18918);
        Map<String, Integer> expected = testStorage;
        Map<String, Integer> actual = TemporaryStorage.temporaryStorage;

        assertEquals(expected, actual);
    }

    @Test
    void performOperation_doThreeSupplyOperation_ok() {
        TemporaryStorage.temporaryStorage.put("lemon", 14800);

        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,"lemon",411);
        operationService.performOperation(fruitTransaction);
        operationService.performOperation(fruitTransaction);
        operationService.performOperation(fruitTransaction);

        testStorage.put("lemon", 16033);
        Map<String, Integer> expected = testStorage;
        Map<String, Integer> actual = TemporaryStorage.temporaryStorage;

        assertEquals(expected, actual);
    }

    @Test
    void performOperation_supplyWithoutOnBalance_ok() {
        TemporaryStorage.temporaryStorage.put("lemon", 14800);
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,"grape", 120);
        operationService.performOperation(fruitTransaction);

        testStorage.put("lemon", 14800);
        testStorage.put("grape", 120);
        Map<String, Integer> expected = testStorage;
        Map<String, Integer> actual = TemporaryStorage.temporaryStorage;
        assertEquals(expected, actual);
    }
}

