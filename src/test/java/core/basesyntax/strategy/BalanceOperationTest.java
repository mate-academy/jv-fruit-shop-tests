package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {

    private final BalanceOperation balanceOperation = new BalanceOperation();

    @BeforeEach
    void setUp() {
        Storage.storage.clear();
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }

    @Test
    void handle_newFruitBalance_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 100);

        balanceOperation.handle(transaction, Storage.storage);

        assertEquals(100, Storage.storage.get("banana"));
    }

    @Test
    void handle_overrideExistingBalance_ok() {
        Storage.storage.put("banana", 50);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 200);

        balanceOperation.handle(transaction, Storage.storage);

        assertEquals(200, Storage.storage.get("banana"));
    }

    @Test
    void handle_nullTransaction_throwIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                balanceOperation.handle(null, Storage.storage)
        );
    }

    @Test
    void handle_nullStorage_throwIllegalArgumentException() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 100);

        assertThrows(IllegalArgumentException.class, () ->
                balanceOperation.handle(transaction, null)
        );
    }
}
