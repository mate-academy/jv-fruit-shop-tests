package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {

    private final ReturnOperation returnOperation = new ReturnOperation();

    @BeforeEach
    void setUp() {
        Storage.storage.clear();
        Storage.storage.put("apple", 100);
    }

    @Test
    void handle_validTransaction_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "apple", 10);

        returnOperation.handle(transaction, Storage.storage);

        assertEquals(110, Storage.storage.get("apple"));
    }

    @Test
    void handle_returnNonExistingFruit_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "banana", 10);

        returnOperation.handle(transaction, Storage.storage);

        assertEquals(10, Storage.storage.get("banana"));
    }

    @Test
    void handle_nullTransaction_throwIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                returnOperation.handle(null, Storage.storage)
        );
    }

    @Test
    void handle_nullStorage_throwIllegalArgumentException() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "apple", 10);

        assertThrows(IllegalArgumentException.class, () ->
                returnOperation.handle(transaction, null)
        );
    }
}
