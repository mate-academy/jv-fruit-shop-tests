package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionTest {
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void beforeAll() {
        fruitTransaction = new FruitTransaction();
    }

    @Test
    public void getOperationFromValue_valueIsNotCorrect_NotOk() {
        String notCorrectValue = "a";
        Assert.assertNull(fruitTransaction.getOperationFromValue(notCorrectValue));
    }

    @Test
    public void getOperationFromValue_valueCorrect_Ok() {
        String notCorrectValue = "b";
        FruitTransaction.Operation expected = FruitTransaction.Operation.BALANCE;
        Assert.assertEquals(expected, fruitTransaction.getOperationFromValue(notCorrectValue));
    }
}
