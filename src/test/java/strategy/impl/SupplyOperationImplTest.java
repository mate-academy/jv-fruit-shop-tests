package strategy.impl;

import static org.junit.Assert.assertEquals;

import db.Storage;
import model.FruitTransaction;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationImplTest {
    private static final String BANANA = "banana";
    private static final int OPERATION_AMOUNT = 10;
    private static FruitTransaction fruitTransactionOk;
    private static FruitTransaction fruitTransactionNotOk;
    private static SupplyOperationImpl supplyOperation;

    @BeforeClass
    public static void beforeAll() {
        fruitTransactionOk = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                BANANA, OPERATION_AMOUNT);
        fruitTransactionNotOk = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                BANANA, OPERATION_AMOUNT);
        supplyOperation = new SupplyOperationImpl();
    }

    @Test
    public void handle_supply_ok() {
        supplyOperation.handler(fruitTransactionOk);
        Integer expected = 10;
        Integer actual = Storage.map.get(fruitTransactionOk.getFruit());
        assertEquals(expected,actual);
    }

    @Test(expected = RuntimeException.class)
    public void handle_not_supplyOperation_notOk() {
        supplyOperation.handler(fruitTransactionNotOk);
        Integer expected = 10;
        Integer actual = Storage.map.get(fruitTransactionNotOk.getFruit());
        assertEquals(expected,actual);
    }

    @After
    public void after() {
        Storage.map.clear();
    }
}
