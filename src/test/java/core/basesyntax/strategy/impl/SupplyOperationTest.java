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
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final int SUPPLY_APPLE_QUANTITY_1 = 50;
    private static final int SUPPLY_APPLE_QUANTITY_2 = 25;
    private static final int SUPPLY_APPLE_QUANTITY_3 = 35;
    private static final int INITIAL_BANANA_QUANTITY = 30;
    private static final int SUPPLY_BANANA_QUANTITY = 20;
    private static final int EXPECTED_APPLE_QUANTITY_AFTER_SINGLE_SUPPLY = 50;
    private static final int EXPECTED_BANANA_QUANTITY_AFTER_SUPPLY = 50;
    private static final int EXPECTED_APPLE_QUANTITY_AFTER_MULTIPLE_SUPPLIES = 60;
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
                FruitTransaction.Operation.SUPPLY, APPLE, SUPPLY_APPLE_QUANTITY_1
        );

        supplyOperation.handle(transaction);

        Map<String, Integer> fruits = Storage.getAllFruits();
        assertEquals(EXPECTED_APPLE_QUANTITY_AFTER_SINGLE_SUPPLY, fruits.get(APPLE));
    }

    @Test
    void handle_existingFruit_shouldIncreaseQuantity() {
        Storage.addFruit(BANANA, INITIAL_BANANA_QUANTITY);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, BANANA, SUPPLY_BANANA_QUANTITY
        );

        supplyOperation.handle(transaction);

        Map<String, Integer> fruits = Storage.getAllFruits();
        assertEquals(EXPECTED_BANANA_QUANTITY_AFTER_SUPPLY, fruits.get(BANANA));
    }

    @Test
    void handle_multipleSupplies_shouldUpdateStorageCorrectly() {
        FruitTransaction transaction1 = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, APPLE, SUPPLY_APPLE_QUANTITY_2
        );
        FruitTransaction transaction2 = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, APPLE, SUPPLY_APPLE_QUANTITY_3
        );

        supplyOperation.handle(transaction1);
        supplyOperation.handle(transaction2);

        Map<String, Integer> fruits = Storage.getAllFruits();
        assertEquals(EXPECTED_APPLE_QUANTITY_AFTER_MULTIPLE_SUPPLIES, fruits.get(APPLE));
    }
}
