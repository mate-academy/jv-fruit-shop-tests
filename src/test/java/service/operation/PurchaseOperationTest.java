package service.operation;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import data.db.Storage;
import model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest extends OperationHandlerTest {
    private static final BalanceOperation balanceOperation = new BalanceOperation();
    private static final FruitTransaction purchaseTransaction = new FruitTransaction();
    private static final int QUANTITY_PURCHASE = 25;
    private static OperationHandler purchaseOperation;

    @BeforeAll
    static void createPurchaseOperation() {
        purchaseOperation = new PurchaseOperation();
        purchaseTransaction.setFruit(DEFAULT_FRUIT);
        purchaseTransaction.setQuantity(QUANTITY_PURCHASE);
    }

    @BeforeEach
    void setDefaultStorage() {
        balanceOperation.handle(defaultTransaction);
        purchaseOperation.handle(purchaseTransaction);
    }

    @Test
    void purchaseHandle_valid_ok() {
        assertTrue(Storage.getFruitStorage().containsKey(DEFAULT_FRUIT)
                && Storage.getFruitStorage().get(DEFAULT_FRUIT) == QUANTITY_PURCHASE);
    }

    @Test
    void purchaseHandle_allQuantityPurchase_ok() {
        purchaseOperation.handle(purchaseTransaction);
        assertTrue(Storage.getFruitStorage().containsKey(DEFAULT_FRUIT)
                && Storage.getFruitStorage().get(DEFAULT_FRUIT) == ZERO_QUANTITY);
    }

    @Test
    void purchaseHandle_zeroQuantity_ok() {
        purchaseOperation.handle(zeroQuantityTransaction);
        assertTrue(Storage.getFruitStorage().containsKey(DEFAULT_FRUIT)
                && Storage.getFruitStorage().get(DEFAULT_FRUIT) == QUANTITY_PURCHASE);
    }

    @Test
    void purchaseHandle_biggerPossibleQuantity_ok() {
        purchaseOperation.handle(defaultTransaction);
        assertTrue(Storage.getFruitStorage().containsKey(DEFAULT_FRUIT)
                && Storage.getFruitStorage().get(DEFAULT_FRUIT) == ZERO_QUANTITY);
    }

    @Test
    void purchaseHandle_nullFruit_notOk() {
        assertThrows(RuntimeException.class, () ->
                purchaseOperation.handle(nullFruitTransaction));
    }

    @Test
    void purchaseHandle_nullTransaction_notOk() {
        assertThrows(RuntimeException.class, () ->
                purchaseOperation.handle(null));
    }
}
