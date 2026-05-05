package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceOperationTest {
    private BalanceOperation balanceOperation;

    @BeforeEach
    void setUp() {
        Storage.getStorage().clear();
        balanceOperation = new BalanceOperation();
    }

    @Test
    void handle_shouldSetBalance() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 100);
        balanceOperation.handle(transaction);
        assertEquals(100, Storage.getStorage().get("apple"));
    }
}
