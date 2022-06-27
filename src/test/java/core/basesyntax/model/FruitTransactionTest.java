package core.basesyntax.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FruitTransactionTest {
    private static FruitTransaction fruitTransaction;

    @Before
    public void setUp() {
        fruitTransaction = new FruitTransaction("b", "banana", 10);
    }

    @Test(expected = RuntimeException.class)
    public void operationIsNull_notOk() {
        fruitTransaction.setOperation(null);
    }

    @Test
    public void operationIsValid_Ok() {
        FruitTransaction.Operation expected = FruitTransaction.Operation.BALANCE;
        FruitTransaction.Operation actual = fruitTransaction.getOperation();
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void fruitIsNull_notOk() {
        fruitTransaction.setFruit(null);
    }

    @Test
    public void get_fruitNameIsValid_Ok() {
        String expected = "banana";
        String actual = fruitTransaction.getFruit();
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void setQuantity_zeroValue_notOk() {
        fruitTransaction.setQuantity(0);
    }

    @Test(expected = RuntimeException.class)
    public void setQuantity_negativeValue_notOk() {
        fruitTransaction.setQuantity(-200);
    }

    @Test
    public void getQuantity_valid_ok() {
        fruitTransaction.setQuantity(1);
        int actual = fruitTransaction.getQuantity();
        int expected = 1;
        Assert.assertEquals(expected, actual);

    }
}
