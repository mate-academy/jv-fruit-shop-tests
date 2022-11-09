package core.basesyntax.service.impl;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionTest {
    private static FruitTransaction fruitTransaction;
    private static String APPLE = "apple";
    private static String EMPTY_LINE = "";
    private static String OPERATION = "apple";
    private static int VALUE = 5;

    @BeforeClass
    public static void beforeClass() {
        fruitTransaction = new FruitTransaction(OPERATION, VALUE, APPLE);
    }

    @Test
    public void fruitTransactions_withCorrectData_ok() {
        String expected = EMPTY_LINE;
        String actual = fruitTransaction.getFruit();
        Assert.assertFalse(actual.equals(expected));
    }

    @Test
    public void fruitTransactions_withUncorrectedData_notOk() {
        String expected = APPLE;
        String actual = fruitTransaction.getFruit();
        Assert.assertTrue(actual.equals(expected));
    }
}
