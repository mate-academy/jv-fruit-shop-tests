package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationImplTest {
    private static final int BALANCE_QUANTITY = 1;
    private static final int PURCHASE_QUANTITY = 1;
    private static final int TOO_MUCH_QUANTITY = 2;
    private static final int RESULT_QUANTITY = 0;
    private static final int NEGATIVE_QUANTITY = -1;
    private static final String BANANA = "banana";
    private final OperationHandler purchaseOperation = new PurchaseOperationImpl();

    @BeforeEach
    void setUp() {
        Storage.clearDb();
    }

    @Test
    void applyOperation_negativePurchaseQuantity_notOk() {
        Storage.updateDb(BANANA, BALANCE_QUANTITY);
        FruitTransaction transaction = new FruitTransaction(
                Operation.PURCHASE,
                BANANA,
                NEGATIVE_QUANTITY);
        assertThrows(RuntimeException.class, () ->
                purchaseOperation.applyOperation(transaction));
    }

    @Test
    void applyOperation_correctPurchaseQuantity_ok() {
        Storage.updateDb(BANANA, BALANCE_QUANTITY);
        FruitTransaction transaction = new FruitTransaction(
                Operation.PURCHASE,
                BANANA,
                PURCHASE_QUANTITY);
        purchaseOperation.applyOperation(transaction);
        assertEquals(RESULT_QUANTITY, Storage.getQuantity(BANANA));
    }

    @Test
    void applyOperation_purchaseTooMuchQuantity_notOk() {
        Storage.updateDb(BANANA, BALANCE_QUANTITY);
        FruitTransaction transaction = new FruitTransaction(
                Operation.PURCHASE,
                BANANA,
                TOO_MUCH_QUANTITY);
        assertThrows(RuntimeException.class, () ->
                purchaseOperation.applyOperation(transaction));
    }
}
