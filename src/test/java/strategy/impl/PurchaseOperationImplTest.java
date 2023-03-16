package strategy.impl;

import static org.junit.Assert.assertEquals;

import db.Storage;
import model.FruitTransaction;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationImplTest {
    private static final String BANANA = "banana";
    private static final int OPERATION_AMOUNT = 10;
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void beforeAll() {
        Storage.map.clear();
    }

    @Test
    public void handle_purchase_ok() {
        Storage.map.put(BANANA, 20);
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                BANANA, OPERATION_AMOUNT);
        new PurchaseOperationImpl().handler(fruitTransaction);
        Integer expected = 10;
        Integer actual = Storage.map.get(fruitTransaction.getFruit());
        assertEquals(expected,actual);
    }

    @Test(expected = RuntimeException.class)
    public void handle_purchaseWithMoreAmount_notOk() {
        Storage.map.put(BANANA, 5);
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                BANANA, OPERATION_AMOUNT);
        new PurchaseOperationImpl().handler(fruitTransaction);
        Integer expected = 10;
        Integer actual = Storage.map.get(fruitTransaction.getFruit());
        assertEquals(expected,actual);
    }

    @Test(expected = RuntimeException.class)
    public void handle_not_purchaseOperation_notOk() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                BANANA, OPERATION_AMOUNT);
        new PurchaseOperationImpl().handler(fruitTransaction);
        Integer expected = 10;
        Integer actual = Storage.map.get(fruitTransaction.getFruit());
        assertEquals(expected,actual);
    }
}
