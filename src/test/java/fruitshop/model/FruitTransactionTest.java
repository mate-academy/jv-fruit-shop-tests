package fruitshop.model;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FruitTransactionTest {
    @Test
    void fruitTransactionWithNegativeAmount_NotOk() {
        int negativeAmount = -1;
        Assert.assertThrows(RuntimeException.class,
                () -> FruitTransaction.of(Operation.RETURN, "banana", negativeAmount));
    }
}
