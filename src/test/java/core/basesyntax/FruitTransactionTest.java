package core.basesyntax;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class FruitTransactionTest {

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
