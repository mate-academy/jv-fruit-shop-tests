package strategy.impl;

import static org.junit.Assert.assertEquals;

import db.Storage;
import model.FruitTransaction;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationImplTest {
    private static final String BANANA = "banana";
    private static final int OPERATION_AMOUNT = 10;
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void beforeAll() {
        Storage.map.clear();
    }

    @Test
    public void handle_balance_ok() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                BANANA, OPERATION_AMOUNT);
        new SupplyOperationImpl().handler(fruitTransaction);
        Integer expected = 10;
        Integer actual = Storage.map.get(fruitTransaction.getFruit());
        assertEquals(expected,actual);
    }

    @Test(expected = RuntimeException.class)
    public void handle_not_balance_notOk() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                BANANA, OPERATION_AMOUNT);
        new SupplyOperationImpl().handler(fruitTransaction);
        Integer expected = 10;
        Integer actual = Storage.map.get(fruitTransaction.getFruit());
        assertEquals(expected,actual);
    }
}
