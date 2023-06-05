package core.basesyntax.strategy.impl;

import static core.basesyntax.model.Product.APPLE;
import static core.basesyntax.model.Product.BANANA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    private static OperationHandler operationHandler;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new SupplyOperationHandler();
    }

    @DisplayName("Calculate with first addition")
    @Test
    public void calculate_firstAddition_ok() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, APPLE.getName(), 10);
        Map<String, Integer> expected = Map.of(APPLE.getName(), 10);
        operationHandler.calculate(transaction);
        Map<String, Integer> actual = Storage.STORAGE_MAP;
        assertEquals(expected, actual);
    }

    @DisplayName("Calculate with key that already exists")
    @Test
    public void calculate_keyAlreadyExist_ok() {
        FruitTransaction firstTransaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, BANANA.getName(), 20);
        FruitTransaction secondTransaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, BANANA.getName(), 10);
        Storage.STORAGE_MAP.put(firstTransaction.getFruit(), firstTransaction.getQuantity());
        operationHandler.calculate(secondTransaction);
        Map<String, Integer> expected = Map.of("banana", 30);
        Map<String, Integer> actual = Storage.STORAGE_MAP;
        assertEquals(expected, actual);
    }

    @AfterEach
    void tearDown() {
        Storage.STORAGE_MAP.clear();
    }
}
