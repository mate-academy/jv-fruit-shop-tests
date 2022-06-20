package core.basesyntax.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FruitTransactionTest {
    private FruitTransaction fruitTransaction;

    @Before
    public void setUp() throws Exception {
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("banana");
        fruitTransaction.setAmount(10);
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
    }

    @Test(expected = RuntimeException.class)
    public void of_nullOperation_notOk() {
        FruitTransaction banana = FruitTransaction.of(null, "banana", 10);
    }

    @Test(expected = RuntimeException.class)
    public void of_nullFruit_notOk() {
        FruitTransaction banana = FruitTransaction.of(
                FruitTransaction.Operation.BALANCE, null, 10);
    }

    @Test(expected = RuntimeException.class)
    public void of_amountIsLessThanZero_notOk() {
        FruitTransaction banana = FruitTransaction.of(
                FruitTransaction.Operation.BALANCE, "banana", -5);
    }

    @Test
    public void of_normalValue_ok() {
        FruitTransaction actual =
                FruitTransaction.of(FruitTransaction.Operation.BALANCE, "banana", 10);
        FruitTransaction expected = fruitTransaction;
        Assert.assertEquals(expected, actual);
    }
}
