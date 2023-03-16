package strategy.impl;

import static org.junit.Assert.assertEquals;

import db.Storage;
import model.FruitTransaction;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationImplTest {
    private static final String BANANA = "banana";
    private static final int OPERATION_AMOUNT = 10;
    private static FruitTransaction fruitTransactionOk;
    private static FruitTransaction fruitTransactionNotOk;
    private static ReturnOperationImpl returnOperation;

    @BeforeClass
    public static void beforeAll() {
        fruitTransactionOk = new FruitTransaction(FruitTransaction.Operation.RETURN,
                BANANA, OPERATION_AMOUNT);
        fruitTransactionNotOk = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                BANANA, OPERATION_AMOUNT);
        returnOperation = new ReturnOperationImpl();
    }

    @Test
    public void handle_return_ok() {
        returnOperation.handler(fruitTransactionOk);
        Integer expected = 10;
        Integer actual = Storage.map.get(fruitTransactionOk.getFruit());
        assertEquals(expected,actual);
    }

    @Test(expected = RuntimeException.class)
    public void handle_not_returnOperation_notOk() {
        returnOperation.handler(fruitTransactionNotOk);
        Integer expected = 10;
        Integer actual = Storage.map.get(fruitTransactionNotOk.getFruit());
        assertEquals(expected,actual);
    }

    @After
    public void after() {
        Storage.map.clear();
    }
}
