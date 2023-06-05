package core.basesyntax.strategy.impl;

import static core.basesyntax.model.Product.APPLE;
import static core.basesyntax.model.Product.BANANA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exeption.QuantityIsNegativeException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private static OperationHandler operationHandler;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new PurchaseOperationHandler();
    }

    @DisplayName("Calculate valid operation with 1 key")
    @Test
    public void calculate_validOperationOneKey_ok() {
        Storage.STORAGE_MAP.put(APPLE.getName(), 50);
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, APPLE.getName(), 20);
        operationHandler.calculate(transaction);
        Map<String, Integer> expected = Map.of(APPLE.getName(), 30);
        Map<String, Integer> actual = Storage.STORAGE_MAP;
        assertEquals(expected, actual);
    }

    @DisplayName("Calculate valid operation with 2 keys")
    @Test
    public void calculate_validOperationTwoKeys_ok() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, APPLE.getName(), 30);
        Storage.STORAGE_MAP.put(APPLE.getName(), 50);
        Storage.STORAGE_MAP.put(BANANA.getName(), 10);
        Map<String, Integer> expected = Map.of(APPLE.getName(), 20, BANANA.getName(), 10);
        operationHandler.calculate(transaction);
        Map<String, Integer> actual = Storage.STORAGE_MAP;
        assertEquals(expected, actual);
    }

    @DisplayName("Calculate invalid operation with negative quantity")
    @Test
    public void calculate_negativeQuantity_notOk() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, APPLE.getName(), 10);
        Storage.STORAGE_MAP.put(APPLE.getName(), 5);
        assertThrows(QuantityIsNegativeException.class, ()
                -> operationHandler.calculate(transaction));
    }

    @DisplayName("Calculate invalid operation with key that doesn't exist")
    @Test
    public void calculate_keyDoesNotExist_notOk() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, BANANA.getName(), 10);
        assertThrows(RuntimeException.class, () -> operationHandler.calculate(transaction));
    }

    @AfterEach
    void tearDown() {
        Storage.STORAGE_MAP.clear();
    }
}
