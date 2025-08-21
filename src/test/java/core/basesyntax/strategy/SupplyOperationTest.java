package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {

    private SupplyOperation operation;

    @BeforeEach
    void setUp() {
        operation = new SupplyOperation();
        Storage.storage.put("apple", 100);
    }

    @AfterEach
     void tearDown() {
        Storage.storage.clear();
    }

    @Test
    void handle_newFruit_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 50);

        operation.handle(transaction, Storage.storage);

        assertEquals(50, Storage.storage.get("banana"));
    }

    @Test
    void handle_existingFruit_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "apple", 50);

        operation.handle(transaction, Storage.storage);

        assertEquals(150, Storage.storage.get("apple"));
    }

    @Test
    void handle_nullTransaction_throwIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                operation.handle(null, Storage.storage)
        );
    }

    @Test
    void handle_nullStorage_throwIllegalArgumentException() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "apple", 50);

        assertThrows(IllegalArgumentException.class, () ->
                operation.handle(transaction, null));
    }
}
