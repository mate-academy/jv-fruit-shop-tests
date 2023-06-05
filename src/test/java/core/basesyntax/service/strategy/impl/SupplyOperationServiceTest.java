package core.basesyntax.service.strategy.impl;

import static core.basesyntax.model.FruitTransaction.Operation.SUPPLY;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.strategy.OperationService;
import core.basesyntax.storage.TemporaryStorage;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupplyOperationServiceTest {
    private static FruitTransaction fruitTransaction;
    private static OperationService operationService;

    @BeforeAll
    static void beforeAll() {
        operationService = new SupplyOperationService();
    }

    @AfterEach
    void tearDown() {
        TemporaryStorage.temporaryStorage.clear();
    }

    @Test
    void performOperation_doOneSupplyOperation_ok() {
        TemporaryStorage.temporaryStorage.put("lemon", 14800);

        fruitTransaction = new FruitTransaction(SUPPLY,"lemon",4118);
        operationService.performOperation(fruitTransaction);

        Map<String, Integer> expected = Map.of("lemon", 18918);
        Map<String, Integer> actual = TemporaryStorage.temporaryStorage;

        assertEquals(expected, actual);
    }

    @Test
    void performOperation_supplyWithoutOnBalance_ok() {
        TemporaryStorage.temporaryStorage.put("lemon", 14800);
        fruitTransaction = new FruitTransaction(SUPPLY,"grape", 120);
        operationService.performOperation(fruitTransaction);

        Map<String, Integer> expected = Map.of("lemon", 14800, "grape", 120);
        Map<String, Integer> actual = TemporaryStorage.temporaryStorage;

        assertEquals(expected, actual);
    }
}
