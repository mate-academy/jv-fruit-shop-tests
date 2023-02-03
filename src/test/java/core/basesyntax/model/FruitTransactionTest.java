package core.basesyntax.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FruitTransactionTest {
    @Test
    public void getByCode_Ok() {
        String code = "r";
        FruitTransaction.Operation expected = FruitTransaction.Operation.RETURN;
        FruitTransaction.Operation actual = FruitTransaction.Operation.getByCode(code);
        assertEquals(actual, expected);
    }

    @Test (expected = RuntimeException.class)
    public void getByCode_NotOk() {
        String code = "a";
        FruitTransaction.Operation.getByCode(code);
    }
}
