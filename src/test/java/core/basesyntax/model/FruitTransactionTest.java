package core.basesyntax.model;

import org.junit.Test;

public class FruitTransactionTest {

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
}
