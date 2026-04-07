package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Storage;
import core.basesyntax.service.strategy.BalanceOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceOperationTest {

    private Storage storage;
    private BalanceOperation balanceOperation;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        balanceOperation = new BalanceOperation(storage);
    }

    @Test
    void handle_validTransaction_ok() {
        FruitTransaction transaction = new FruitTransaction(100, "banana",
                FruitTransaction.Operation.BALANCE);

        balanceOperation.handle(transaction);

        assertEquals(100, storage.getStorage().get("banana"));
    }

    @Test
    void handle_twoDifferentFruits_ok() {
        FruitTransaction transaction1 = new FruitTransaction(100,"banana",
                FruitTransaction.Operation.BALANCE);
        FruitTransaction transaction2 = new FruitTransaction(50,"apple",
                FruitTransaction.Operation.BALANCE);

        balanceOperation.handle(transaction1);
        balanceOperation.handle(transaction2);

        assertEquals(100, storage.getStorage().get("banana"));
        assertEquals(50, storage.getStorage().get("apple"));
    }

    @Test
    void handle_overwriteExistingBalance_ok() {
        FruitTransaction transaction1 = new FruitTransaction(100, "banana",
                FruitTransaction.Operation.BALANCE);
        FruitTransaction transaction2 = new FruitTransaction(50, "banana",
                FruitTransaction.Operation.BALANCE);

        balanceOperation.handle(transaction1);
        balanceOperation.handle(transaction2);

        assertEquals(50, storage.getStorage().get("banana"));
    }

    @Test
    void handle_nullTransaction_notOk() {
        assertThrows(RuntimeException.class, () -> balanceOperation.handle(null));
    }

    @Test
    void handle_emptyFruitName_notOk() {
        assertThrows(NullPointerException.class, () -> {
            FruitTransaction transaction = new FruitTransaction(100, null,
                    FruitTransaction.Operation.BALANCE);
            balanceOperation.handle(transaction);
        });
    }

    @Test
    void handle_nullQuantity_notOk() {
        assertThrows(NullPointerException.class, () -> {
            FruitTransaction transaction = new FruitTransaction(null, "apple",
                    FruitTransaction.Operation.BALANCE);
            balanceOperation.handle(transaction);
        });
    }
}
