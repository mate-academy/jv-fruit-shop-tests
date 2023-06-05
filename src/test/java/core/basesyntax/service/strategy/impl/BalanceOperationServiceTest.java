package core.basesyntax.service.strategy.impl;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.strategy.OperationService;
import core.basesyntax.storage.TemporaryStorage;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceOperationServiceTest {
    private static FruitTransaction fruitTransaction;
    private static OperationService operationService;

    @BeforeAll
    static void beforeAll() {
        operationService = new BalanceOperationService();
    }

    @AfterEach
    void tearDown() {
        TemporaryStorage.temporaryStorage.clear();
    }

    @Test
    void performOperation_doOneBalanceOperation_ok() {
        fruitTransaction = new FruitTransaction(BALANCE,"apple",500);
        operationService.performOperation(fruitTransaction);

        Map<String, Integer> expected = Map.of("apple", 500);
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
