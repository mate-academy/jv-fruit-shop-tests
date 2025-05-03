package core.basesyntax.strategy.impl;

import static core.basesyntax.constants.Constants.BALANCE_QUANTITY;
import static core.basesyntax.constants.Constants.BANANA;
import static core.basesyntax.constants.Constants.NEGATIVE_QUANTITY;
import static core.basesyntax.constants.Constants.PURCHASE_QUANTITY;
import static core.basesyntax.constants.Constants.PURCHASE_RESULT_QUANTITY;
import static core.basesyntax.constants.Constants.TOO_MUCH_QUANTITY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationImplTest {
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
        assertEquals(PURCHASE_RESULT_QUANTITY, Storage.getQuantity(BANANA));
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
