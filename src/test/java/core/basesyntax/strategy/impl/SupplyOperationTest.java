package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.StorageService;
import core.basesyntax.service.impl.StorageServiceImpl;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private StorageService storageService;
    private SupplyOperation supplyOperation;

    @BeforeEach
    void setUp() {
        storageService = new StorageServiceImpl();
        supplyOperation = new SupplyOperation(storageService);
        Storage.clear();
    }

    @Test
    void handle_validTransaction_shouldAddFruitToStorage() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "apple", 50
        );

        supplyOperation.handle(transaction);

        Map<String, Integer> fruits = Storage.getAllFruits();
        assertEquals(50, (int) fruits.get("apple"));
    }

    @Test
    void handle_existingFruit_shouldIncreaseQuantity() {
        Storage.addFruit("banana",30);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 20
        );

        supplyOperation.handle(transaction);

        Map<String, Integer> fruits = Storage.getAllFruits();
        assertEquals(50, (int) fruits.get("banana"));
    }

    @Test
    void handle_multipleSupplies_shouldUpdateStorageCorrectly() {
        FruitTransaction transaction1 = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "apple", 25
        );
        FruitTransaction transaction2 = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "apple", 35
        );

        supplyOperation.handle(transaction1);
        supplyOperation.handle(transaction2);

        Map<String, Integer> fruits = Storage.getAllFruits();
        assertEquals(60, (int) fruits.get("apple"));
    }
}
