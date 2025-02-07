package core.basesyntax.strategy.operation;

import core.basesyntax.data.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private final BalanceOperation balanceOperation = new BalanceOperation();

    @AfterEach
    void tearDown() {
        Storage.clear();
    }

    @Test
    void apply_validBalanceTransaction_updatesStorage() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 50);
        balanceOperation.apply(transaction);

        Assertions.assertEquals(50, Storage.getInventory().get("apple"));
    }
}
