package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationTest {
    private BalanceOperation balanceOperation;
    private PurchaseOperation purchaseOperation;

    @BeforeEach
    void setUp() {
        Storage.getStorage().clear();
        balanceOperation = new BalanceOperation();
        purchaseOperation = new PurchaseOperation();
    }

    @Test
    void handle_shouldSubstractFromBalance() {
        FruitTransaction balanceTx = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 100);
        balanceOperation.handle(balanceTx);
        FruitTransaction purchaseTx = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 50);
        purchaseOperation.handle(purchaseTx);
        assertEquals(50, Storage.getStorage().get("apple"));
    }
}
