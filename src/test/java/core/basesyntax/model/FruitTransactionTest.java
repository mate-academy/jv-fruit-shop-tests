package core.basesyntax.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FruitTransactionTest {
    private static FruitTransaction fruitTransaction;

    @Before
    public void setUp() {
        fruitTransaction = new FruitTransaction("b", "banana", 1);
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
    public void fruitNameIsValid_Ok() {
        String expected = "banana";
        String actual = fruitTransaction.getFruit();
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void fruitAmountIsZero_notOk() {
        fruitTransaction.setQuantity(0);
    }

    @Test(expected = RuntimeException.class)
    public void fruitAmountIsNegative_notOk() {
        fruitTransaction.setQuantity(-200);
    }

    @Test
    public void fruitAmountIsValid_isOk() {
        fruitTransaction.setQuantity(1);
    }
}
