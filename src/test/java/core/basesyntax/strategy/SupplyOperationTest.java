package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyOperationTest {
    private BalanceOperation balanceOperation;
    private SupplyOperation supplyOperation;

    @BeforeEach
    void setUp() {
        Storage.getStorage().clear();
        balanceOperation = new BalanceOperation();
        supplyOperation = new SupplyOperation();
    }

    @Test
    void handle_shouldAddToBalance() {
        FruitTransaction balanceTx = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 100);
        balanceOperation.handle(balanceTx);
        FruitTransaction supplyTx = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "apple", 100);
        supplyOperation.handle(supplyTx);
        assertEquals(200, Storage.getStorage().get("apple"));
    }
}
