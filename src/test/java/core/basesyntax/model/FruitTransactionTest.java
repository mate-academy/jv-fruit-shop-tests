package core.basesyntax.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FruitTransactionTest {
    @Test
    public void getByCode_ok() {
        String code = "b";
        FruitTransaction.Operation expected = FruitTransaction.Operation.BALANCE;
        FruitTransaction.Operation actual = FruitTransaction.Operation.getByCode(code);
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void getByInvalidCode_notOk() {
        String code = "1";
        FruitTransaction.Operation.getByCode(code);
    }

    @Test (expected = RuntimeException.class)
    public void getByNullCode_notOk() {
        String code = null;
        FruitTransaction.Operation.getByCode(code);
    }
}
