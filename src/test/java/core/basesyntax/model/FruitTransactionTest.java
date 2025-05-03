package core.basesyntax.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FruitTransactionTest {
    @Test
    public void getByCode_validCode_Ok() {
        String code = "b";
        FruitTransaction.Operation expected = FruitTransaction.Operation.BALANCE;
        FruitTransaction.Operation actual = FruitTransaction.Operation.getByCode(code);
        assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getByCode_invalidCode_NotOk() {
        String code = "";
        FruitTransaction.Operation.getByCode(code);
    }
}
