package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.StorageService;
import core.basesyntax.service.impl.StorageServiceImpl;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private StorageService storageService;
    private BalanceOperation balanceOperation;

    @BeforeEach
    void setUp() {
        storageService = new StorageServiceImpl();
        Storage.clear();
        balanceOperation = new BalanceOperation(storageService);

    }

    @Test
    void handle_validTransaction_shouldAddFruitToStorage() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 100
        );

        balanceOperation.handle(transaction);

        Map<String, Integer> fruits = Storage.getAllFruits();
        assertEquals(100, (int) fruits.get("apple"));
    }

    @Test
    void handle_existingFruit_shouldIncreaseQuantity() {
        Storage.addFruit("apple",50);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 100
        );

        balanceOperation.handle(transaction);

        Map<String, Integer> fruits = Storage.getAllFruits();
        assertEquals(150, (int) fruits.get("apple"));
    }

    @Test
    void handle_multipleFruits_shouldAddAllFruits() {
        FruitTransaction appleTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 100
        );
        FruitTransaction bananaTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 50
        );

        balanceOperation.handle(appleTransaction);
        balanceOperation.handle(bananaTransaction);

        Map<String, Integer> fruits = Storage.getAllFruits();
        assertEquals(100, (int) fruits.get("apple"));
        assertEquals(50, (int) fruits.get("banana"));
    }
}
