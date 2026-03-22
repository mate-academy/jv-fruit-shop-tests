package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PurchaseOperationTest {

    private BalanceOperation balanceOperation;
    private PurchaseOperation purchaseOperation;

    @BeforeEach
    void setUp() {
        Storage.clear();
        balanceOperation = new BalanceOperation();
        purchaseOperation = new PurchaseOperation();
        balanceOperation.apply(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 150));

    }

    @Test
    public void shouldDecreaseQuantityWhenPurchase() {
        purchaseOperation.apply(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana", 100));
        FruitTransaction fruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana", 100);
        int actual = Storage.getQuantity("banana");
        assertEquals(50, actual);
    }
}