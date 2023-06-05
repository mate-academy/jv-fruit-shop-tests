package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.db.StorageImpl;
import core.basesyntax.exception.InvalidOperatioExeption;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private static SupplyOperation supplyOperation;

    @BeforeAll
    static void setUp() {
        supplyOperation = new SupplyOperation(new StorageImpl());
    }

    @Test
    void process_positiveQuantity_successfullyUpdatedStorage() {
        Storage.storage.put("banana", 100);
        FruitTransaction fruitTransaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "banana", 50);
        supplyOperation.process(fruitTransaction);
        int updatedQuantity = Storage.storage.get("banana");
        assertEquals(150, updatedQuantity);
    }

    @Test
    void process_negativeQuantity_throwsInvalidOperationException() {
        FruitTransaction fruitTransaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "apple", -50);
        assertThrows(InvalidOperatioExeption.class,
                () -> supplyOperation.process(fruitTransaction));
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }
}
