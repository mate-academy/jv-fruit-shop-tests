package core.basesyntax;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class FruitTransactionTest {
    @Test
    public void constructor_andGetters_ok() {
        FruitTransaction.Operation operation = FruitTransaction.Operation.SUPPLY;
        String fruit = "banana";
        int amount = 20;
        FruitTransaction transaction = new FruitTransaction(operation, fruit,amount);

        Assert.assertEquals(operation, transaction.getOperation());
        Assert.assertEquals(fruit, transaction.getFruit());
        Assert.assertEquals(amount, transaction.getAmount());
    }

    @Test
    public void setter_validValues_ok() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 10);
        transaction.setOperation(FruitTransaction.Operation.RETURN);
        transaction.setFruit("banana");
        transaction.setAmount(55);

        Assert.assertEquals(FruitTransaction.Operation.RETURN, transaction.getOperation());
        Assert.assertEquals("banana", transaction.getFruit());
        Assert.assertEquals(55, transaction.getAmount());
    }

    @Test
    public void testFromCodeValid_ok() {
        Assert.assertEquals(FruitTransaction.Operation.BALANCE,
                FruitTransaction.Operation.fromCode("b"));
        Assert.assertEquals(FruitTransaction.Operation.SUPPLY,
                FruitTransaction.Operation.fromCode("s"));
        Assert.assertEquals(FruitTransaction.Operation.PURCHASE,
                FruitTransaction.Operation.fromCode("p"));
        Assert.assertEquals(FruitTransaction.Operation.RETURN,
                FruitTransaction.Operation.fromCode("r"));
    }

    @Test
    public void testFromCode_notOk() {
        Exception exception = Assert.assertThrows(IllegalArgumentException.class, () -> {
            FruitTransaction.Operation.fromCode("x");
        });

        Assert.assertEquals("Unknown operation ", exception.getMessage());
    }
}
