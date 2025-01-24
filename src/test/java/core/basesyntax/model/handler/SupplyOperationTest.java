package core.basesyntax.model.handler;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private static SupplyOperation supplyOperation;

    @BeforeAll
    static void beforeAll() {
        supplyOperation = new SupplyOperation();
    }

    @BeforeEach
    void setUp() {
        Storage.getStorage().clear();
    }

    @Test
    void supplyNullFruitTransactionNotOk() {
        assertThrows(RuntimeException.class, () -> supplyOperation.handle(null));
    }

    @Test
    void supplyOk() {
        Storage.getStorage().put("banana", 100);
        supplyOperation.handle(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "banana", 57));
        Map.Entry<String, Integer> expectedEntry = Map.entry(
                "banana", 157);
        assertTrue(Storage.getStorage().entrySet().contains(expectedEntry));
    }

    @Test
    void supplyMultipleFruitsOk() {
        Storage.getStorage().put("orange", 50);
        supplyOperation.handle(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "orange", 30));
        Map.Entry<String, Integer> expectedEntry = Map.entry(
                "orange", 80);
        assertTrue(Storage.getStorage().entrySet().contains(expectedEntry));
    }
}
