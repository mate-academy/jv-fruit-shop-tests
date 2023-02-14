package core.basesyntax.fruitscontent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class FruitTransactionTest {
    @Test
    public void getByCode_Ok() {
        String code = "b";
        FruitTransaction.Operation expected = FruitTransaction.Operation.BALANCE;
        FruitTransaction.Operation actual = FruitTransaction.Operation.getByCode(code);
        assertEquals(expected, actual);
    }

    @Test
    public void getByCode_NotOk() {
        String code = "n";
        try {
            FruitTransaction.Operation.getByCode(code);
        } catch (RuntimeException e) {
            return;
        }
        fail("Something went wrong with your value...");
    }
}
