package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {

    private BalanceOperation operation;

    @BeforeEach
    void setUp() {
        operation = new BalanceOperation();
        Storage.storage.put("apple", 100);
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }

    @Test
    void handle_newFruitBalance_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 100);

        operation.handle(transaction, Storage.storage);

        assertEquals(100, Storage.storage.get("banana"));
    }

    @Test
    void handle_overrideExistingBalance_ok() {
        Storage.storage.put("banana", 50);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 200);

        operation.handle(transaction, Storage.storage);

        assertEquals(200, Storage.storage.get("banana"));
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
                FruitTransaction.Operation.BALANCE, "banana", 100);

        assertThrows(IllegalArgumentException.class, () ->
                operation.handle(transaction, null));
    }
}
