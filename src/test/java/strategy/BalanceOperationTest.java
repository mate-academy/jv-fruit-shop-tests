package strategy;

import static org.junit.Assert.assertEquals;

import db.Storage;
import model.FruitTransaction;
import org.junit.Before;
import org.junit.Test;

public class BalanceOperationTest {
    private static final String APPLE = "apple";
    private static final Integer AMOUNT_TWO = 20;
    private static final String BANANA = "banana";
    private static final Integer AMOUNT_APPLE = 10;
    private static final Integer AMOUNT_THREE = 15;
    private static BalanceOperation balanceOperation;

    @Before
    public void setUp() {
        balanceOperation = new BalanceOperation();
    }

    @Before
    public void cleanStorage() {
        Storage.clearStorage();
    }

    @Test
    public void testDoOperation_AddsTransactionQuantityToStorage() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction
                .Operation.BALANCE, APPLE, AMOUNT_APPLE);
        Storage.storeFruit(APPLE, AMOUNT_TWO);
        balanceOperation.doOperation(transaction);
        int updatedStorageAmount = Storage.getFruitsNumber(APPLE);
        assertEquals(30, updatedStorageAmount);
    }

    @Test
    public void testDoOperation_AddsTransactionQuantityToStorageWithZeroInitialAmount() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction
                .Operation.BALANCE, BANANA, AMOUNT_THREE);
        Storage.storeFruit(BANANA, 0);
        balanceOperation.doOperation(transaction);
        int updatedStorageAmount = Storage.getFruitsNumber(BANANA);
        assertEquals(15, updatedStorageAmount);
    }
}
