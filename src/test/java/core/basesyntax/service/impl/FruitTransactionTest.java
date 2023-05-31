package core.basesyntax.service.impl;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionTest {
    private static FruitTransaction fruitTransaction;
    private static int QUANTITY = 5;
    private static String FRUIT = "apple";
    private static String EMPTY_LINE = "";
    private static String OPERATION = "b";

    @BeforeClass
    public static void beforeClass() {
        fruitTransaction = new FruitTransaction(OPERATION, QUANTITY, FRUIT);
    }

    @Test
    public void fruitTransactions_withUncorrectedData_notOk() {
        String expected = EMPTY_LINE;
        String actual = fruitTransaction.getFruit();
        Assert.assertFalse(actual.equals(expected));
    }

    @Test
    public void fruitTransactions_withUncorrectedData_ok() {
        String expected = FRUIT;
        String actual = fruitTransaction.getFruit();
        Assert.assertTrue(actual.equals(expected));
    }

    @Test
    public void fruitTransactions_withOneData_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(OPERATION);
        String operation = fruitTransaction.getOperation();
        Assert.assertEquals(OPERATION, operation);
    }

    @Test
    public void fruitTransactions_withTwoData_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(QUANTITY, FRUIT);
        FruitTransaction fruitTransactionSecond = new FruitTransaction(OPERATION, QUANTITY);
        Assert.assertEquals(FRUIT, fruitTransaction.getFruit());
        Assert.assertEquals(QUANTITY, fruitTransaction.getQuantity());
        Assert.assertEquals(OPERATION, fruitTransactionSecond.getOperation());
        Assert.assertEquals(QUANTITY, fruitTransactionSecond.getQuantity());
    }
}
