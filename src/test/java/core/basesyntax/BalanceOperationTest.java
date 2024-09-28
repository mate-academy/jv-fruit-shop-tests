package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operations.BalanceOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class BalanceOperationTest {

    private final Storage storage = new Storage();
    private final BalanceOperation balanceOperation = new BalanceOperation(storage);

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
