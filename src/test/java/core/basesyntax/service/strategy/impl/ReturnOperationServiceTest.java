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

class ReturnOperationServiceTest {
    private static Map<String, Integer> testStorage;
    private static FruitTransaction fruitTransaction;
    private static OperationService operationService;

    @BeforeAll
    static void beforeAll() {
        operationService = new ReturnOperationService();
        testStorage = new HashMap<>();
    }

    @AfterEach
    void tearDown() {
        testStorage.clear();
        TemporaryStorage.temporaryStorage.clear();
    }

    @Test
    void performOperation_doOneReturnOperation_ok() {
        TemporaryStorage.temporaryStorage.put("lemon", 7111);

        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.RETURN,"lemon",1);
        operationService.performOperation(fruitTransaction);

        testStorage.put("lemon", 7112);
        Map<String, Integer> expected = testStorage;
        Map<String, Integer> actual = TemporaryStorage.temporaryStorage;

        assertEquals(expected, actual);
    }

    @Test
    void performOperation_doThreeReturnOperation_ok() {
        TemporaryStorage.temporaryStorage.put("lemon", 7111);

        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.RETURN,"lemon",44);
        operationService.performOperation(fruitTransaction);
        operationService.performOperation(fruitTransaction);
        operationService.performOperation(fruitTransaction);

        testStorage.put("lemon", 7243);
        Map<String, Integer> expected = testStorage;
        Map<String, Integer> actual = TemporaryStorage.temporaryStorage;

        assertEquals(expected, actual);
    }

    @Test
    void performOperation_returnWithoutOnBalance_ok() {
        TemporaryStorage.temporaryStorage.put("lemon", 7111);
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.RETURN,"grape", 86);
        assertThrows(RuntimeException.class,
                () -> operationService.performOperation(fruitTransaction),
                "\"RuntimeException\" should be thrown when fruit for return is not on balance");

    }
}

