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
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final int QUANTITY_50 = 50;
    private static final int QUANTITY_100 = 100;
    private static final int QUANTITY_150 = 150;
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
                FruitTransaction.Operation.BALANCE, APPLE, QUANTITY_100
        );

        balanceOperation.handle(transaction);

        Map<String, Integer> fruits = Storage.getAllFruits();
        assertEquals(QUANTITY_100, (int) fruits.get(APPLE));
    }

    @Test
    void handle_existingFruit_shouldIncreaseQuantity() {
        Storage.addFruit(APPLE, QUANTITY_50);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, APPLE, QUANTITY_100
        );

        balanceOperation.handle(transaction);

        Map<String, Integer> fruits = Storage.getAllFruits();
        assertEquals(QUANTITY_150, (int) fruits.get(APPLE));
    }

    @Test
    void handle_multipleFruits_shouldAddAllFruits() {
        FruitTransaction appleTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, APPLE, QUANTITY_100
        );
        FruitTransaction bananaTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, BANANA, QUANTITY_50
        );

        balanceOperation.handle(appleTransaction);
        balanceOperation.handle(bananaTransaction);

        Map<String, Integer> fruits = Storage.getAllFruits();
        assertEquals(QUANTITY_100, (int) fruits.get(APPLE));
        assertEquals(QUANTITY_50, (int) fruits.get(BANANA));
    }
}
