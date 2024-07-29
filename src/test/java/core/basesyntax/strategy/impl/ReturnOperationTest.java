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
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final int RETURN_APPLE_QUANTITY_1 = 50;
    private static final int RETURN_APPLE_QUANTITY_2 = 25;
    private static final int RETURN_APPLE_QUANTITY_3 = 35;
    private static final int INITIAL_BANANA_QUANTITY = 30;
    private static final int RETURN_BANANA_QUANTITY = 20;
    private static final int EXPECTED_APPLE_QUANTITY_AFTER_SINGLE_RETURN = 50;
    private static final int EXPECTED_BANANA_QUANTITY_AFTER_RETURN = 50;
    private static final int EXPECTED_APPLE_QUANTITY_AFTER_MULTIPLE_RETURNS = 60;
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
                FruitTransaction.Operation.RETURN, APPLE, RETURN_APPLE_QUANTITY_1
        );

        returnOperation.handle(transaction);

        Map<String, Integer> fruits = Storage.getAllFruits();
        assertEquals(EXPECTED_APPLE_QUANTITY_AFTER_SINGLE_RETURN, (int) fruits.get(APPLE));
    }

    @Test
    void handle_existingFruit_shouldIncreaseQuantity() {
        Storage.addFruit(BANANA, INITIAL_BANANA_QUANTITY);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, BANANA, RETURN_BANANA_QUANTITY
        );

        returnOperation.handle(transaction);

        Map<String, Integer> fruits = Storage.getAllFruits();
        assertEquals(EXPECTED_BANANA_QUANTITY_AFTER_RETURN, (int) fruits.get(BANANA));
    }

    @Test
    void handle_multipleReturns_shouldUpdateStorageCorrectly() {
        FruitTransaction transaction1 = new FruitTransaction(
                FruitTransaction.Operation.RETURN, APPLE, RETURN_APPLE_QUANTITY_2
        );
        FruitTransaction transaction2 = new FruitTransaction(
                FruitTransaction.Operation.RETURN, APPLE, RETURN_APPLE_QUANTITY_3
        );

        returnOperation.handle(transaction1);
        returnOperation.handle(transaction2);

        Map<String, Integer> fruits = Storage.getAllFruits();
        assertEquals(EXPECTED_APPLE_QUANTITY_AFTER_MULTIPLE_RETURNS, (int) fruits.get(APPLE));
    }
}
