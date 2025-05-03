package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private static OperationHandler handler;

    @BeforeAll
    static void beforeAll() {
        handler = new PurchaseOperationHandler();
    }

    @Test
    public void doCalculation_validOperation_ok() {
        Storage.dataStorage.put("apple", 50);
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20);
        handler.doCalculation(transaction);
        Map<String, Integer> expected = Map.of("apple", 30);
        Map<String, Integer> actual = Storage.dataStorage;
        assertEquals(expected, actual);
    }

    @Test
    public void doCalculation_validOperationTwoKeys_ok() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 30);
        Storage.dataStorage.put("apple", 50);
        Storage.dataStorage.put("banana", 40);
        Map<String, Integer> expected = Map.of("apple", 20, "banana", 40);
        handler.doCalculation(transaction);
        Map<String, Integer> actual = Storage.dataStorage;
        assertEquals(expected, actual);
    }

    @Test
    public void doCalculation_keyDoesNotExist_notOk() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 14);
        assertThrows(RuntimeException.class, () -> handler.doCalculation(transaction));
    }

    @Test
    public void doCalculation_negativeQuantity_notOk() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "pear", 100);
        Storage.dataStorage.put("pear", 50);
        assertThrows(RuntimeException.class, () -> handler.doCalculation(transaction));
    }

    @AfterEach
    void tearDown() {
        Storage.dataStorage.clear();
    }
}
