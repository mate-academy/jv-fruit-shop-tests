package core.basesyntax.models;

import org.junit.Assert;
import org.junit.Test;

public class TestFruitTransaction {

    @Test
    public void testCreateFruitTransactionNullType_notOk() {
        Assert.assertThrows(RuntimeException.class,
                () -> FruitTransaction.of(null, "banana", 30));
        Assert.assertThrows(RuntimeException.class,
                () -> FruitTransaction.of(FruitTransaction.TypeOfActivity.BALANCE, null, 30));
        Assert.assertThrows(RuntimeException.class,
                () -> FruitTransaction.of(FruitTransaction.TypeOfActivity.SUPPLY, "", 30));
        Assert.assertThrows(RuntimeException.class,
                () -> FruitTransaction.of(FruitTransaction.TypeOfActivity.BALANCE, "banana", -10));
    }

    @Test
    public void testFruitTransaction_ToString_Ok() {
        FruitTransaction transaction = FruitTransaction.of(
                FruitTransaction.TypeOfActivity.BALANCE, "banana", 30);
        String expected = "FruitTransaction{type=BALANCE, name='banana', quantity=30}";
        Assert.assertEquals(expected, transaction.toString());
    }

}
