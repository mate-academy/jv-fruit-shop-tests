package core.basesyntax.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BalanceOperationTest {

    private static Storage storage;
    private static BalanceOperation balanceOperation;

    @BeforeAll
    public static void setUp() {
        storage = new Storage();
        balanceOperation = new BalanceOperation(storage);
    }

    @AfterEach
    public void clearStorage() {
        storage.clear();
    }

    @Test
    public void apply_balanceOperation_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                "apple", 100);
        balanceOperation.apply(transaction);
        assertEquals(100, storage.getQuantity("apple"));
    }

    @Test
    public void apply_negativeBalance_throwsException() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", -100);
        assertThrows(IllegalArgumentException.class, () -> balanceOperation.apply(transaction));
    }
}
