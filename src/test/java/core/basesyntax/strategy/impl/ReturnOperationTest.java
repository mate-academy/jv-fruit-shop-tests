package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.StorageService;
import core.basesyntax.service.impl.StorageServiceImpl;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private StorageService storageService;
    private ReturnOperation returnOperation;

    @BeforeEach
    void setUp() {
        storageService = new StorageServiceImpl();
        returnOperation = new ReturnOperation(storageService);
        Storage.clear();
    }

    @Test
    void handle_validTransaction_shouldAddFruitToStorage() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "apple", 50
        );

        returnOperation.handle(transaction);

        Map<String, Integer> fruits = Storage.getAllFruits();
        assertEquals(50, (int) fruits.get("apple"));
    }

    @Test
    void handle_existingFruit_shouldIncreaseQuantity() {
        Storage.addFruit("banana",30);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "banana", 20
        );

        returnOperation.handle(transaction);

        Map<String, Integer> fruits = Storage.getAllFruits();
        assertEquals(50, (int) fruits.get("banana"));
    }

    @Test
    void handle_multipleReturns_shouldUpdateStorageCorrectly() {
        FruitTransaction transaction1 = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "apple", 25
        );
        FruitTransaction transaction2 = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "apple", 35
        );

        returnOperation.handle(transaction1);
        returnOperation.handle(transaction2);

        Map<String, Integer> fruits = Storage.getAllFruits();
        assertEquals(60, (int) fruits.get("apple"));
    }
}
