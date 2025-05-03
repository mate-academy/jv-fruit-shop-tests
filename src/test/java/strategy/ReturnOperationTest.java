package strategy;

import static org.junit.Assert.assertEquals;

import db.Storage;
import model.FruitTransaction;
import org.junit.Before;
import org.junit.Test;

public class ReturnOperationTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private PurchaseOperation purchaseOperation;

    @Before
    public void setUp() {
        purchaseOperation = new PurchaseOperation();
    }

    @Test
    public void doOperation_DeductsTransactionQuantityFromStorage() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction
                .Operation.PURCHASE, APPLE, 5);
        Storage.storeFruit(APPLE, 20);
        purchaseOperation.doOperation(transaction);
        int updatedStorageAmount = Storage.getFruitsNumber(APPLE);
        assertEquals(15, updatedStorageAmount);
    }

    @Test
    public void doOperation_DeductsTransactionQuantityFromStorageWithZeroInitialAmount() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction
                .Operation.PURCHASE, BANANA, 8);
        Storage.storeFruit(BANANA, 0);
        purchaseOperation.doOperation(transaction);
        int updatedStorageAmount = Storage.getFruitsNumber(BANANA);
        assertEquals(-8, updatedStorageAmount);
    }
}
