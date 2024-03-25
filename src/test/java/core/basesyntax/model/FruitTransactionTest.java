package core.basesyntax.model;

import org.junit.Assert;
import org.junit.Test;

public class FruitTransactionTest {
    @Test
    public void constructor_ValidInput_ShouldSetFieldsCorrectly() {
        FruitTransaction.Operation expectedOperation = FruitTransaction.Operation.BALANCE;
        String expectedFruit = "banana";
        int expectedQuantity = 50;

        FruitTransaction fruitTransaction = new FruitTransaction(expectedOperation,
                expectedFruit, expectedQuantity);

        Assert.assertEquals(expectedOperation, fruitTransaction.getOperation());
        Assert.assertEquals(expectedFruit, fruitTransaction.getFruit());
        Assert.assertEquals(expectedQuantity, fruitTransaction.getQuantity());
    }
}
