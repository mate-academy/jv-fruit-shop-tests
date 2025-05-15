package core.basesyntax.services.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.models.FruitTransaction;
import core.basesyntax.models.FruitTransaction.Operation;
import core.basesyntax.services.StorageService;
import core.basesyntax.services.StorageServiceImp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private StorageService storageService;
    private SupplyOperation supplyOperation;

    @BeforeEach
    void setUp() {
        storageService = new StorageServiceImp();
        storageService.clear();
        supplyOperation = new SupplyOperation(storageService);
    }

    @AfterEach
    void tearDown() {
        storageService.clear();
    }

    @Test
    void apply_validNewFruit_addsQuantity() {
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, "banana", 40);
        supplyOperation.apply(transaction);
        assertEquals(40, storageService.getQuantity("banana"));
    }

    @Test
    void apply_existingFruit_incrementsQuantity() {
        storageService.add("apple", 15);
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, "apple", 25);
        supplyOperation.apply(transaction);
        assertEquals(40, storageService.getQuantity("apple"));
    }

    @Test
    void apply_zeroQuantity_storesAsZero() {
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, "kiwi", 0);
        supplyOperation.apply(transaction);
        assertEquals(0, storageService.getQuantity("kiwi"));
    }

    @Test
    void apply_nullFruitName_throwsIllegalArgumentException() {
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, null, 10);
        assertThrows(IllegalArgumentException.class, () -> supplyOperation.apply(transaction));
    }

    @Test
    void apply_negativeQuantity_throwsIllegalArgumentException() {
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, "orange", -10);
        assertThrows(IllegalArgumentException.class, () -> supplyOperation.apply(transaction));
    }
}
