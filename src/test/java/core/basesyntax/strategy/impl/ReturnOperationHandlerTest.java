package core.basesyntax.strategy.impl;

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

class ReturnOperationHandlerTest {
    private static OperationHandler operationHandler;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new ReturnOperationHandler();
    }

    @DisplayName("Calculate with first addition")
    @Test
    public void calculate_firstAddition_ok() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN, BANANA.getName(), 50);
        Map<String, Integer> expected = Map.of(BANANA.getName(), 50);
        operationHandler.calculate(transaction);
        Map<String, Integer> actual = Storage.STORAGE_MAP;
        assertEquals(expected, actual);
    }

    @DisplayName("Calculate with key that already exists")
    @Test
    public void calculate_keyAlreadyExist_ok() {
        FruitTransaction firstTransaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN, BANANA.getName(), 15);
        FruitTransaction secondTransaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN, BANANA.getName(), 10);
        Storage.STORAGE_MAP.put(firstTransaction.getFruit(), firstTransaction.getQuantity());
        operationHandler.calculate(secondTransaction);
        Map<String, Integer> expected = Map.of(BANANA.getName(), 25);
        Map<String, Integer> actual = Storage.STORAGE_MAP;
        assertEquals(expected, actual);
    }

    @AfterEach
    void tearDown() {
        Storage.STORAGE_MAP.clear();
    }
}
