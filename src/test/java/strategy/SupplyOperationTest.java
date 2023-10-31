package strategy;

import static org.junit.Assert.assertEquals;

import db.Storage;
import model.FruitTransaction;
import model.FruitTransaction.Operation;
import org.junit.Before;
import org.junit.Test;

public class SupplyOperationTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private SupplyOperation supplyOperation;

    @Before
    public void setUp() {
        supplyOperation = new SupplyOperation();
    }

    @Test
    public void testDoOperation_AddsTransactionQuantityToStorage() {
        FruitTransaction transaction = new FruitTransaction(Operation
                .SUPPLY, APPLE, 5);
        Storage.storeFruit(APPLE, 10);
        supplyOperation.doOperation(transaction);
        int updatedStorageAmount = Storage.getFruitsNumber(APPLE);
        assertEquals(15, updatedStorageAmount);
    }

    @Test
    public void testDoOperation_AddsTransactionQuantityToStorageWithZeroInitialAmount() {
        FruitTransaction transaction = new FruitTransaction(Operation
                .SUPPLY, BANANA, 3);
        Storage.storeFruit(BANANA, 0);
        supplyOperation.doOperation(transaction);
        int updatedStorageAmount = Storage.getFruitsNumber(BANANA);
        assertEquals(3, updatedStorageAmount);
    }
}
